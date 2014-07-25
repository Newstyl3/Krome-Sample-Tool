/***************************************************************************
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
24	#ifndef LIBGIG_KORG_H
25	#define LIBGIG_KORG_H
26	
27	#include "RIFF.h"
28	#include "gig.h" // for struct buffer_t
29	#include <vector>
30	
31	/**
32	 * @brief KORG sound format specific classes and definitions
33	 *
34	 * Classes in this namespace provide access to Korg's sample based instrument
35	 * files which are used by Korg Trinity, Triton, OASYS, M3 and Kronos
36	 * synthesizer keyboards.
37	 *
38	 * At the moment these classes only support read support, but no write support
39	 * yet.
40	 *
41	 * Sample based instruments are spread in KORG's format over individual files:
42	 *
43	 * - @e .KSF @e Sample @e File (KSFSample): contains exactly one audio sample
44	 *   (mono). So each audio sample is stored in its own .KSF file. It also stores
45	 *   some basic meta informations for the sample, i.e. loop points.
46	 *
47	 * - @e .KMP @e Multi @e Sample @e File (KMPInstrument): groups individual .KSF
48	 *   sample files to one logical group of samples. This file just references the
49	 *   actual .KSF files by file name. It also provides some articulation
50	 *   informations, for example it maps the individual samples to regions on the
51	 *   keyboard, but it does not even provide support for velocity splits or
52	 *   layers.
53	 *
54	 * The upper two file types are used by KORG for many years and their internal
55	 * file format has remained nearly unchanged over that long period, and has also
56	 * remained consistent over many different synthesizer keyboard models and
57	 * series. Due to this however, the articulation informations stored in those
58	 * two files are too primitive for being used directly on modern keyboards.
59	 * That's why the following file type exists as well:
60	 *
61	 * - @e .PCG @e Program @e File: contains a complete bank list of "programs"
62	 *   (sounds / virtual instruments) and "combinations" (combi sounds), along
63	 *   with all their detailed articulation informations and references to the
64	 *   .KSF sample files. The precise internal format of this file type differs
65	 *   quite a lot between individual keyboard models, series and even between
66	 *   different OS versions of the same keyboard model. The individual sound
67	 *   definitions in this file references the individual (separate) sound files,
68	 *   defines velocity splits, groups individual mono samples to stereo samples
69	 *   and stores all synthesis model specific sound settings like envelope
70	 *   generator settings, LFO settings, MIDI controllers, filter settings, etc.
71	 *
72	 * Unfortunately this library does not provide support for .PCG files yet.
73	 */
74	namespace Korg {
75	
76	    typedef std::string String;
77	
78	    class KMPRegion;
79	    class KMPInstrument;
80	
81	    typedef gig::buffer_t buffer_t;
82	
83	    /**
84	     * @brief .KSF audio sample file
85	     *
86	     * Implements access to KORG audio sample files with ".KSF" file name
87	     * extension. As of to date, there are only mono samples in .KSF format.
88	     * If you ever encounter a stereo sample, please report it!
89	     */
90	    class KSFSample {
91	    public:
92	        String Name; ///< Sample name for drums (since this name is always stored with 16 bytes, this name must never be longer than 16 characters).
93	        uint8_t DefaultBank; ///< 0..3
94	        uint32_t Start;
95	        uint32_t Start2;
96	        uint32_t LoopStart;
97	        uint32_t LoopEnd;
98	        uint32_t SampleRate; ///< i.e. 44100
99	        uint8_t  Attributes; ///< Bit field of flags, better call IsCompressed(), CompressionID() and Use2ndStart() instead of accessing this variable directly.
100	        int8_t   LoopTune; ///< -99..+99
101	        uint8_t  Channels; ///< Number of audio channels (seems to be always 1, thus Mono for all Korg sound files ATM).
102	        uint8_t  BitDepth; ///< i.e. 8 or 16
103	        uint32_t SamplePoints; ///< Currently the library expects all Korg samples to be Mono, thus the value here might be incorrect in case you ever find a Korg sample in Stereo. If you got a Stereo one, please report it!
104	
105	        KSFSample(const String& filename);
106	        virtual ~KSFSample();
107	        int FrameSize() const;
108	        bool IsCompressed() const;
109	        uint8_t CompressionID() const;
110	        bool Use2ndStart() const;
111	        String FileName() const;
112	        buffer_t GetCache() const;
113	        buffer_t LoadSampleData();
114	        buffer_t LoadSampleData(unsigned long SampleCount);
115	        buffer_t LoadSampleDataWithNullSamplesExtension(uint NullSamplesCount);
116	        buffer_t LoadSampleDataWithNullSamplesExtension(unsigned long SampleCount, uint NullSamplesCount);
117	        void ReleaseSampleData();
118	        unsigned long SetPos(unsigned long SampleCount, RIFF::stream_whence_t Whence = RIFF::stream_start);
119	        unsigned long GetPos() const;
120	        unsigned long Read(void* pBuffer, unsigned long SampleCount);
121	    private:
122	        RIFF::File* riff;
123	        buffer_t RAMCache; ///< Buffers sample data in RAM.
124	    };
125	
126	    /**
127	     * @brief Region of a .KMP multi sample file
128	     *
129	     * Encapsulates one region on the keyboard which is part of a KORG ".KMP"
130	     * file (KMPInstrument). Each regions defines a mapping between one (mono)
131	     * sample and one consecutive area on the keyboard.
132	     */
133	    class KMPRegion {
134	    public:
135	        bool Transpose;
136	        uint8_t OriginalKey; ///< Note of sample's original pitch, a.k.a. "root key" (0..127).
137	        uint8_t TopKey; ///< The end of this region on the keyboard (0..127). The start of this region is given by TopKey+1 of the previous region.
138	        int8_t Tune; ///< -99..+99 cents
139	        int8_t Level; ///< -99..+99 cents
140	        int8_t Pan; ///< -64..+63
141	        int8_t FilterCutoff; ///< -50..0
142	        String SampleFileName; ///< Base file name of sample file (12 bytes). Call FullSampleFileName() for getting the file name with path, which you might then pass to a KSFSample constructor to load the respective sample.
143	        
144	        String FullSampleFileName() const;
145	        KMPInstrument* GetInstrument() const;
146	    protected:
147	        KMPRegion(KMPInstrument* parent, RIFF::Chunk* rlp1);
148	        virtual ~KMPRegion();
149	        friend class KMPInstrument;
150	    private:
151	        KMPInstrument* parent;
152	        RIFF::Chunk* rlp1;
153	    };
154	
155	    /**
156	     * @brief .KMP multi sample file
157	     *
158	     * Implements access to so called KORG "multi sample" files with ".KMP" file
159	     * name extension. A KMPInstrument combines individual KSFSamples (.KSF
160	     * files) to a logical group of samples. It also maps the individual (mono)
161	     * samples to consecutive areas (KMPRegion) on the keyboard.
162	     *
163	     * A KMPInstrument only supports very simple articulation informations.
164	     * Too simple for decent sounds. That's why this kind of file is usually not
165	     * used directly on keyboards. Instead a keyboard uses a separate .PCG file
166	     * for its more complex and very synthesis model specific and OS version
167	     * specific articulation definitions.
168	     *
169	     * There is no support for .PCG files in this library yet though.
170	     */
171	    class KMPInstrument {
172	    public:
173	        String Name16; ///< Human readable name of the instrument for display purposes (not the file name). Since this name is always stored with 16 bytes, this name must never be longer than 16 characters. This information is stored in all .KMP files (@see Name()).
174	        String Name24; ///< Longer Human readable name (24 characters) of the instrument for display purposes (not the file name). This longer name might not be stored in all versions of .KMP files (@see Name()).
175	        uint8_t Attributes; ///< Bit field of attribute flags (ATM only bit 0 is used, better call Use2ndStart() instead of accessing this variable directly though).
176	
177	        KMPInstrument(const String& filename);
178	        virtual ~KMPInstrument();
179	        KMPRegion* GetRegion(int index);
180	        int GetRegionCount() const;
181	        bool Use2ndStart() const;
182	        String FileName() const;
183	        String Name() const;
184	    private:
185	        RIFF::File* riff;
186	        std::vector<KMPRegion*> regions;
187	    };
188	
189	    /**
190	     * @brief Korg format specific exception
191	     *
192	     * Will be thrown whenever a Korg format specific error occurs while trying
193	     * to access such a file in this format. Note: In your application you
194	     * should better catch for RIFF::Exception rather than this one, except you
195	     * explicitly want to catch and handle Korg::Exception and RIFF::Exception
196	     * independently from each other, which usually shouldn't be necessary
197	     * though.
198	     */
199	    class Exception : public RIFF::Exception {
200	    public:
201	        Exception(String Message);
202	        void PrintMessage();
203	    };
204	
205	    String libraryName();
206	    String libraryVersion();
207	
208	} // namespace Korg
209	
210	#endif // LIBGIG_KORG_H
