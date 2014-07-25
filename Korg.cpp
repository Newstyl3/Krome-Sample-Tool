
1	/***************************************************************************
2	 *                                                                         *
3	 *   Copyright (C) 2014 Christian Schoenebeck                              *
4	 *                      <cuse@users.sourceforge.net>                       *
5	 *                                                                         *
6	 *   This library is part of libgig.                                       *
7	 *                                                                         *
8	 *   This library is free software; you can redistribute it and/or modify  *
9	 *   it under the terms of the GNU General Public License as published by  *
10	 *   the Free Software Foundation; either version 2 of the License, or     *
11	 *   (at your option) any later version.                                   *
12	 *                                                                         *
13	 *   This library is distributed in the hope that it will be useful,       *
14	 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
15	 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
16	 *   GNU General Public License for more details.                          *
17	 *                                                                         *
18	 *   You should have received a copy of the GNU General Public License     *
19	 *   along with this library; if not, write to the Free Software           *
20	 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston,                 *
21	 *   MA  02111-1307  USA                                                   *
22	 ***************************************************************************/
23	
24	#include "Korg.h"
25	
26	#include <string.h> // for memset()
27	
28	#if WORDS_BIGENDIAN
29	# define CHUNK_ID_MSP1  0x4d535031
30	# define CHUNK_ID_RLP1  0x524c5031
31	# define CHUNK_ID_SMP1  0x534d5031
32	# define CHUNK_ID_SMD1  0x534d4431
33	# define CHUNK_ID_NAME  0x4e414d45
34	#else  // little endian
35	# define CHUNK_ID_MSP1  0x3150534d
36	# define CHUNK_ID_RLP1  0x31504c52
37	# define CHUNK_ID_SMP1  0x31504d53
38	# define CHUNK_ID_SMD1  0x31444d53
39	# define CHUNK_ID_NAME  0x454d414e
40	#endif // WORDS_BIGENDIAN
41	
42	#define SMD1_CHUNK_HEADER_SZ    12
43	
44	namespace Korg {
45	
46	    #if defined(WIN32)
47	    static const String PATH_SEP = "\\";
48	    #else
49	    static const String PATH_SEP = "/";
50	    #endif
51	
52	// *************** Internal functions **************
53	// *
54	
55	    template<unsigned int SZ>
56	    inline String readText(RIFF::Chunk* ck) {
57	        char buf[SZ+1] = {};
58	        int n = ck->Read(buf, SZ, 1);
59	        if (n != SZ)
60	            throw Exception("Premature end while reading text field");
61	        String s = buf;
62	        return s;
63	    }
64	
65	    /// Read 24 bytes of ASCII text from given chunk and return it as String.
66	    inline String readText24(RIFF::Chunk* ck) {
67	        return readText<24>(ck);
68	    }
69	
70	    /// Read 16 bytes of ASCII text from given chunk and return it as String.
71	    inline String readText16(RIFF::Chunk* ck) {
72	        return readText<16>(ck);
73	    }
74	
75	    /// Read 12 bytes of ASCII text from given chunk and return it as String.
76	    inline String readText12(RIFF::Chunk* ck) {
77	        return readText<12>(ck);
78	    }
79	
80	    /// For example passing "FOO.KMP" will return "FOO".
81	    inline String removeFileTypeExtension(const String& filename) {
82	        size_t pos = filename.find_last_of('.');
83	        if (pos == String::npos) return filename;
84	        return filename.substr(0, pos);
85	    }
86	
87	// *************** KSFSample ***************
88	// *
89	
90	    KSFSample::KSFSample(const String& filename) {
91	        RAMCache.Size              = 0;
92	        RAMCache.pStart            = NULL;
93	        RAMCache.NullExtensionSize = 0;
94	
95	        riff = new RIFF::File(
96	            filename, CHUNK_ID_SMP1, RIFF::endian_big, RIFF::layout_flat
97	        );
98	        
99	        // read 'SMP1' chunk
100	        RIFF::Chunk* smp1 = riff->GetSubChunk(CHUNK_ID_SMP1);
101	        if (!smp1)
102	            throw Exception("Not a Korg sample file ('SMP1' chunk not found)");
103	        if (smp1->GetSize() < 32)
104	            throw Exception("Not a Korg sample file ('SMP1' chunk size too small)");
105	        Name = readText16(smp1);
106	        DefaultBank = smp1->ReadUint8();
107	        Start = smp1->ReadUint8() << 16 | smp1->ReadUint8() << 8 | smp1->ReadUint8();
108	        Start2    = smp1->ReadUint32();
109	        LoopStart = smp1->ReadUint32();
110	        LoopEnd   = smp1->ReadUint32();
111	
112	        // read 'SMD1' chunk
113	        RIFF::Chunk* smd1 = riff->GetSubChunk(CHUNK_ID_SMD1);
114	        if (!smd1)
115	            throw Exception("Not a Korg sample file ('SMD1' chunk not found)");
116	        if (smd1->GetSize() < 12)
117	            throw Exception("Not a Korg sample file ('SMD1' chunk size too small)");
118	        SampleRate   = smd1->ReadUint32();
119	        Attributes   = smd1->ReadUint8();
120	        LoopTune     = smd1->ReadInt8();
121	        Channels     = smd1->ReadUint8();
122	        BitDepth     = smd1->ReadUint8();
123	        SamplePoints = smd1->ReadUint32();
124	    }
125	
126	    KSFSample::~KSFSample() {
127	        if (RAMCache.pStart) delete[] (int8_t*) RAMCache.pStart;
128	        if (riff) delete riff;
129	    }
130	
131	    /**
132	     * Loads the whole sample wave into RAM. Use ReleaseSampleData() to free
133	     * the memory if you don't need the cached sample data anymore.
134	     *
135	     * @returns  buffer_t structure with start address and size of the buffer
136	     *           in bytes
137	     * @see      ReleaseSampleData(), Read(), SetPos()
138	     */
139	    buffer_t KSFSample::LoadSampleData() {
140	        return LoadSampleDataWithNullSamplesExtension(this->SamplePoints, 0); // 0 amount of NullSamples
141	    }
142	
143	    /**
144	     * Reads and caches the first \a SampleCount numbers of SamplePoints in RAM.
145	     * Use ReleaseSampleData() to free the memory space if you don't need the
146	     * cached samples anymore.
147	     *
148	     * @param SampleCount - number of sample points to load into RAM
149	     * @returns             buffer_t structure with start address and size of
150	     *                      the cached sample data in bytes
151	     * @see                 ReleaseSampleData(), Read(), SetPos()
152	     */
153	    buffer_t KSFSample::LoadSampleData(unsigned long SampleCount) {
154	        return LoadSampleDataWithNullSamplesExtension(SampleCount, 0); // 0 amount of NullSamples
155	    }
156	
157	    /**
158	     * Loads the whole sample wave into RAM. Use ReleaseSampleData() to free the
159	     * memory if you don't need the cached sample data anymore. The method will
160	     * add \a NullSamplesCount silence samples past the official buffer end
161	     * (this won't affect the 'Size' member of the buffer_t structure, that
162	     * means 'Size' always reflects the size of the actual sample data, the
163	     * buffer might be bigger though). Silence samples past the official buffer
164	     * are needed for differential algorithms that always have to take
165	     * subsequent samples into account (resampling/interpolation would be an
166	     * important example) and avoids memory access faults in such cases.
167	     *
168	     * @param NullSamplesCount - number of silence samples the buffer should
169	     *                           be extended past it's data end
170	     * @returns                  buffer_t structure with start address and
171	     *                           size of the buffer in bytes
172	     * @see                      ReleaseSampleData(), Read(), SetPos()
173	     */
174	    buffer_t KSFSample::LoadSampleDataWithNullSamplesExtension(uint NullSamplesCount) {
175	        return LoadSampleDataWithNullSamplesExtension(this->SamplePoints, NullSamplesCount);
176	    }
177	
178	    /**
179	     * Reads and caches the first \a SampleCount numbers of SamplePoints in RAM.
180	     * Use ReleaseSampleData() to free the memory space if you don't need the
181	     * cached samples anymore. The method will add \a NullSamplesCount silence
182	     * samples past the official buffer end (this won't affect the 'Size' member
183	     * of the buffer_t structure, that means 'Size' always reflects the size of
184	     * the actual sample data, the buffer might be bigger though). Silence
185	     * samples past the official buffer are needed for differential algorithms
186	     * that always have to take subsequent samples into account
187	     * (resampling/interpolation would be an important example) and avoids
188	     * memory access faults in such cases.
189	     *
190	     * @param SampleCount      - number of sample points to load into RAM
191	     * @param NullSamplesCount - number of silence samples the buffer should
192	     *                           be extended past it's data end
193	     * @returns                  buffer_t structure with start address and
194	     *                           size of the cached sample data in bytes
195	     * @see                      ReleaseSampleData(), Read(), SetPos()
196	     */
197	    buffer_t KSFSample::LoadSampleDataWithNullSamplesExtension(unsigned long SampleCount, uint NullSamplesCount) {
198	        if (SampleCount > this->SamplePoints) SampleCount = this->SamplePoints;
199	        if (RAMCache.pStart) delete[] (int8_t*) RAMCache.pStart;
200	        unsigned long allocationsize = (SampleCount + NullSamplesCount) * FrameSize();
201	        SetPos(0); // reset read position to beginning of sample
202	        RAMCache.pStart            = new int8_t[allocationsize];
203	        RAMCache.Size              = Read(RAMCache.pStart, SampleCount) * FrameSize();
204	        RAMCache.NullExtensionSize = allocationsize - RAMCache.Size;
205	        // fill the remaining buffer space with silence samples
206	        memset((int8_t*)RAMCache.pStart + RAMCache.Size, 0, RAMCache.NullExtensionSize);
207	        return GetCache();
208	    }
209	
210	    /**
211	     * Returns current cached sample points. A buffer_t structure will be
212	     * returned which contains address pointer to the begin of the cache and
213	     * the size of the cached sample data in bytes. Use LoadSampleData() to
214	     * cache a specific amount of sample points in RAM.
215	     *
216	     * @returns  buffer_t structure with current cached sample points
217	     * @see      LoadSampleData();
218	     */
219	    buffer_t KSFSample::GetCache() const {
220	        // return a copy of the buffer_t structure
221	        buffer_t result;
222	        result.Size              = this->RAMCache.Size;
223	        result.pStart            = this->RAMCache.pStart;
224	        result.NullExtensionSize = this->RAMCache.NullExtensionSize;
225	        return result;
226	    }
227	
228	    /**
229	     * Frees the cached sample from RAM if loaded with LoadSampleData()
230	     * previously.
231	     *
232	     * @see  LoadSampleData();
233	     */
234	    void KSFSample::ReleaseSampleData() {
235	        if (RAMCache.pStart) delete[] (int8_t*) RAMCache.pStart;
236	        RAMCache.pStart = NULL;
237	        RAMCache.Size   = 0;
238	        RAMCache.NullExtensionSize = 0;
239	    }
240	
241	    /**
242	     * Sets the position within the sample (in sample points, not in
243	     * bytes). Use this method and <i>Read()</i> if you don't want to load
244	     * the sample into RAM, thus for disk streaming.
245	     *
246	     * @param SampleCount  number of sample points to jump
247	     * @param Whence       optional: to which relation \a SampleCount refers
248	     *                     to, if omited <i>RIFF::stream_start</i> is assumed
249	     * @returns            the new sample position
250	     * @see                Read()
251	     */
252	    unsigned long KSFSample::SetPos(unsigned long SampleCount, RIFF::stream_whence_t Whence) {
253	        unsigned long samplePos = GetPos();
254	        switch (Whence) {
255	            case RIFF::stream_curpos:
256	                samplePos += SampleCount;
257	                break;
258	            case RIFF::stream_end:
259	                samplePos = this->SamplePoints - 1 - SampleCount;
260	                break;
261	            case RIFF::stream_backward:
262	                samplePos -= SampleCount;
263	                break;
264	            case RIFF::stream_start:
265	            default:
266	                samplePos = SampleCount;
267	                break;
268	        }
269	        if (samplePos > this->SamplePoints) samplePos = this->SamplePoints;
270	        unsigned long bytes = samplePos * FrameSize();
271	        RIFF::Chunk* smd1 = riff->GetSubChunk(CHUNK_ID_SMD1);
272	        unsigned long result = smd1->SetPos(SMD1_CHUNK_HEADER_SZ + bytes);
273	        return (result - SMD1_CHUNK_HEADER_SZ) / FrameSize();
274	    }
275	
276	    /**
277	     * Returns the current position in the sample (in sample points).
278	     */
279	    unsigned long KSFSample::GetPos() const {
280	        RIFF::Chunk* smd1 = riff->GetSubChunk(CHUNK_ID_SMD1);
281	        return (smd1->GetPos() - SMD1_CHUNK_HEADER_SZ) / FrameSize();
282	    }
283	
284	    /**
285	     * Reads \a SampleCount number of sample points from the current
286	     * position into the buffer pointed by \a pBuffer and increments the
287	     * position within the sample. Use this method and SetPos() if you don't
288	     * want to load the sample into RAM, thus for disk streaming.
289	     *
290	     * <b>Caution:</b> If you are using more than one streaming thread, you
291	     * have to use an external decompression buffer for <b>EACH</b>
292	     * streaming thread to avoid race conditions and crashes (which is not
293	     * implemented for this class yet)!
294	     *
295	     * @param pBuffer      destination buffer
296	     * @param SampleCount  number of sample points to read
297	     * @returns            number of successfully read sample points
298	     * @see                SetPos()
299	     */
300	    unsigned long KSFSample::Read(void* pBuffer, unsigned long SampleCount) {
301	        RIFF::Chunk* smd1 = riff->GetSubChunk(CHUNK_ID_SMD1);
302	
303	        unsigned long samplestoread = SampleCount, totalreadsamples = 0, readsamples;
304	
305	        if (samplestoread) do {
306	            readsamples       = smd1->Read(pBuffer, SampleCount, FrameSize()); // FIXME: channel inversion due to endian correction?
307	            samplestoread    -= readsamples;
308	            totalreadsamples += readsamples;
309	        } while (readsamples && samplestoread);
310	
311	        return totalreadsamples;
312	    }
313	
314	    /**
315	     * Returns the size of one sample point of this sample in bytes.
316	     */
317	    int KSFSample::FrameSize() const {
318	        return BitDepth / 8 * Channels;
319	    }
320	
321	    uint8_t KSFSample::CompressionID() const {
322	        return Attributes & 0x04;
323	    }
324	
325	    bool KSFSample::IsCompressed() const {
326	        return Attributes & 0x10;
327	    }
328	
329	    bool KSFSample::Use2ndStart() const {
330	        return !(Attributes & 0x20);
331	    }
332	
333	    String KSFSample::FileName() const {
334	        return riff->GetFileName();
335	    }
336	
337	// *************** KMPRegion ***************
338	// *
339	
340	    KMPRegion::KMPRegion(KMPInstrument* parent, RIFF::Chunk* rlp1)
341	        : parent(parent), rlp1(rlp1)
342	    {
343	        OriginalKey = rlp1->ReadUint8();
344	        Transpose   = (OriginalKey >> 7) & 1;
345	        OriginalKey &= 0x7F;
346	        TopKey = rlp1->ReadUint8() & 0x7F;
347	        Tune   = rlp1->ReadInt8();
348	        Level  = rlp1->ReadInt8();
349	        Pan    = rlp1->ReadUint8() & 0x7F;
350	        FilterCutoff = rlp1->ReadInt8();
351	        SampleFileName = readText12(rlp1);
352	    }
353	
354	    KMPRegion::~KMPRegion() {
355	    }
356	
357	    String KMPRegion::FullSampleFileName() const {
358	        return removeFileTypeExtension(rlp1->GetFile()->GetFileName()) +
359	               PATH_SEP + SampleFileName;
360	    }
361	
362	    KMPInstrument* KMPRegion::GetInstrument() const {
363	        return parent;
364	    }
365	
366	// *************** KMPInstrument ***************
367	// *
368	
369	    KMPInstrument::KMPInstrument(const String& filename) {
370	        riff = new RIFF::File(
371	            filename, CHUNK_ID_MSP1, RIFF::endian_big, RIFF::layout_flat
372	        );
373	
374	        // read 'MSP1' chunk
375	        RIFF::Chunk* msp1 = riff->GetSubChunk(CHUNK_ID_MSP1);
376	        if (!msp1)
377	            throw Exception("Not a Korg instrument file ('MSP1' chunk not found)");
378	        if (msp1->GetSize() < 18)
379	            throw Exception("Not a Korg instrument file ('MSP1' chunk size too small)");
380	        Name16 = readText16(msp1);
381	        int nSamples = msp1->ReadUint8();
382	        Attributes   = msp1->ReadUint8();
383	        
384	        // read optional 'NAME' chunk
385	        RIFF::Chunk* name = riff->GetSubChunk(CHUNK_ID_NAME);
386	        if (name) {
387	            Name24 = readText24(name);
388	        }
389	
390	        // read 'RLP1' chunk ...
391	        RIFF::Chunk* rlp1 = riff->GetSubChunk(CHUNK_ID_RLP1);
392	        if (!rlp1)
393	            throw Exception("Not a Korg instrument file ('RLP1' chunk not found)");
394	        if (rlp1->GetSize() < 18 * nSamples)
395	            throw Exception("Not a Korg instrument file ('RLP1' chunk size too small)");
396	        for (int i = 0; i < nSamples; ++i) {
397	            KMPRegion* region = new KMPRegion(this, rlp1);
398	            regions.push_back(region);
399	        }
400	    }
401	
402	    KMPInstrument::~KMPInstrument() {
403	        if (riff) delete riff;
404	        for (int i = 0; i < regions.size(); ++i)
405	            delete regions[i];
406	    }
407	
408	    KMPRegion* KMPInstrument::GetRegion(int index) {
409	        if (index < 0 || index >= regions.size())
410	            return NULL;
411	        return regions[index];
412	    }
413	
414	    int KMPInstrument::GetRegionCount() const {
415	        return regions.size();
416	    }
417	
418	    bool KMPInstrument::Use2ndStart() const {
419	        return !(Attributes & 1);
420	    }
421	
422	    /**
423	     * Returns the long name (Name24) if it was stored in the file, otherwise
424	     * returns the short name (Name16).
425	     */
426	    String KMPInstrument::Name() const {
427	        return (!Name24.empty()) ? Name24 : Name16;
428	    }
429	
430	    String KMPInstrument::FileName() const {
431	        return riff->GetFileName();
432	    }
433	
434	// *************** Exception ***************
435	// *
436	
437	    Exception::Exception(String Message) : RIFF::Exception(Message) {
438	    }
439	
440	    void Exception::PrintMessage() {
441	        std::cout << "Korg::Exception: " << Message << std::endl;
442	    }
443	    
444	// *************** functions ***************
445	// *
446	
447	    /**
448	     * Returns the name of this C++ library. This is usually "libgig" of
449	     * course. This call is equivalent to RIFF::libraryName() and
450	     * gig::libraryName().
451	     */
452	    String libraryName() {
453	        return PACKAGE;
454	    }
455	
456	    /**
457	     * Returns version of this C++ library. This call is equivalent to
458	     * RIFF::libraryVersion() and gig::libraryVersion().
459	     */
460	    String libraryVersion() {
461	        return VERSION;
462	    }
463	
464	} // namespace Korg
