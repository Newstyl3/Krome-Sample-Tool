
1	/***************************************************************************
2	 *                                                                         *
3	 *   Copyright (C) 2014 Christian Schoenebeck                              *
4	 *                      <cuse@users.sourceforge.net>                       *
5	 *                                                                         *
6	 *   This program is part of libgig.                                       *
7	 *                                                                         *
8	 *   This program is free software; you can redistribute it and/or modify  *
9	 *   it under the terms of the GNU General Public License as published by  *
10	 *   the Free Software Foundation; either version 2 of the License, or     *
11	 *   (at your option) any later version.                                   *
12	 *                                                                         *
13	 *   This program is distributed in the hope that it will be useful,       *
14	 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
15	 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
16	 *   GNU General Public License for more details.                          *
17	 *                                                                         *
18	 *   You should have received a copy of the GNU General Public License     *
19	 *   along with this program; if not, write to the Free Software           *
20	 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston,                 *
21	 *   MA  02111-1307  USA                                                   *
22	 ***************************************************************************/
23	
24	#ifdef HAVE_CONFIG_H
25	# include <config.h>
26	#endif
27	
28	#include <iostream>
29	#include <cstdlib>
30	#include <string>
31	#include <set>
32	
33	#include "Korg.h"
34	
35	using namespace std;
36	
37	static string Revision() {
38	    string s = "$Revision$";
39	    return s.substr(11, s.size() - 13); // cut dollar signs, spaces and CVS macro keyword
40	}
41	
42	static void printVersion() {
43	    cout << "korgdump revision " << Revision() << endl;
44	    cout << "using " << Korg::libraryName() << " " << Korg::libraryVersion() << endl;
45	}
46	
47	static void printUsage() {
48	    cout << "korgdump - parses Korg sound files and prints out their content." << endl;
49	    cout << endl;
50	    cout << "Usage: korgdump [-v] FILE" << endl;
51	    cout << endl;
52	    cout << "   -v  Print version and exit." << endl;
53	    cout << endl;
54	}
55	
56	static bool endsWith(const string& haystack, const string& needle) {
57	    return haystack.substr(haystack.size() - needle.size(), needle.size()) == needle;
58	}
59	
60	static void printSample(const string& filename, int i = -1) {
61	    Korg::KSFSample* smpl = new Korg::KSFSample(filename);
62	    cout << "        ";
63	    if (i != -1) cout << (i+1) << ". ";
64	    cout << "Sample SampleFile='" << smpl->FileName() << "'" << endl;
65	    cout << "            Name='" << smpl->Name << "'" << endl;
66	    cout << "            Start=" << smpl->Start << ", Start2=" << smpl->Start2 << ", LoopStart=" << smpl->LoopStart << ", LoopEnd=" << smpl->LoopEnd << endl;
67	    cout << "            SampleRate=" << smpl->SampleRate << ", LoopTune=" << (int)smpl->LoopTune << ", Channels=" << (int)smpl->Channels << ", BitDepth=" << (int)smpl->BitDepth << ", SamplePoints=" << smpl->SamplePoints << endl;
68	    cout << "            IsCompressed=" << smpl->IsCompressed() << ", CompressionID=" << (int)smpl->CompressionID() << ", Use2ndStart=" << (int)smpl->Use2ndStart() << endl;
69	    cout << endl;
70	    delete smpl;
71	}
72	
73	static void printRegion(int i, Korg::KMPRegion* rgn) {
74	    cout << "        " << (i+1) << ". Region SampleFile='" << rgn->FullSampleFileName() << "'" << endl;
75	    cout << "            OriginalKey=" << (int)rgn->OriginalKey << ", TopKey=" << (int)rgn->TopKey << endl;
76	    cout << "            Transpose=" << rgn->Transpose << ", Tune=" << (int)rgn->Tune << ", Level=" << (int)rgn->Level << ", Pan=" << (int)rgn->Pan << endl;
77	    cout << "            FilterCutoff=" << (int)rgn->FilterCutoff << endl;
78	    cout << endl;
79	}
80	
81	static void printInstrument(Korg::KMPInstrument* instr) {
82	    cout << "Instrument '" << instr->Name() << "'" << endl;
83	    cout << "    Use2ndStart=" << instr->Use2ndStart() << endl;
84	    cout << endl;
85	    set<string> sampleFileNames;
86	    for (int i = 0; i < instr->GetRegionCount(); ++i) {
87	        Korg::KMPRegion* rgn = instr->GetRegion(i);
88	        printRegion(i, rgn);
89	        sampleFileNames.insert(rgn->FullSampleFileName());
90	    }
91	
92	    cout << "Samples referenced by instrument:" << endl;
93	    cout << endl;
94	
95	    int i = 0;
96	    for (set<string>::iterator it = sampleFileNames.begin();
97	         it != sampleFileNames.end(); ++it, ++i)
98	    {
99	        printSample(*it, i);
100	    }
101	}
102	
103	int main(int argc, char *argv[]) {
104	    if (argc <= 1) {
105	        printUsage();
106	        return EXIT_FAILURE;
107	    }
108	    if (argv[1][0] == '-') {
109	        switch (argv[1][1]) {
110	            case 'v':
111	                printVersion();
112	                return EXIT_SUCCESS;
113	        }
114	    }
115	    const char* filename = argv[1];
116	    FILE* hFile = fopen(filename, "r");
117	    if (!hFile) {
118	        cout << "Invalid file argument (could not open given file for reading)!" << endl;
119	        return EXIT_FAILURE;
120	    }
121	    fclose(hFile);
122	    try {
123	        if (endsWith(filename, ".KMP")) {
124	            Korg::KMPInstrument* instr = new Korg::KMPInstrument(filename);
125	            printInstrument(instr);
126	            delete instr;
127	        } else if (endsWith(filename, ".KSF")) {
128	            printSample(filename);
129	        } else if (endsWith(filename, ".PCG")) {
130	            cout << "There is no support for .PCG files in this version of korgdump yet." << endl;
131	            return EXIT_FAILURE;
132	        } else {
133	            cout << "Unknown file type (file name postfix)" << endl;
134	            return EXIT_FAILURE;
135	        }
136	    }
137	    catch (RIFF::Exception e) {
138	        e.PrintMessage();
139	        return EXIT_FAILURE;
140	    }
141	    catch (...) {
142	        cout << "Unknown exception while trying to parse file." << endl;
143	        return EXIT_FAILURE;
144	    }
145	
146	    return EXIT_SUCCESS;
147	}
