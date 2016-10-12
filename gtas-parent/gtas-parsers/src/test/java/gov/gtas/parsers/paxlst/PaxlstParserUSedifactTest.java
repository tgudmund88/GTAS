/*
 * All GTAS code is Copyright 2016, Unisys Corporation.
 * 
 * Please see LICENSE.txt for details.
 */
package gov.gtas.parsers.paxlst;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gov.gtas.parsers.edifact.EdifactParser;
import gov.gtas.parsers.exception.ParseException;
import gov.gtas.parsers.vo.ApisMessageVo;
import gov.gtas.parsers.vo.FlightVo;


public final class PaxlstParserUSedifactTest {
    EdifactParser<ApisMessageVo> parser; 
    
    @Before
    public void setUp() {
        this.parser = new PaxlstParserUSedifact();
    }

    @Test
    public void testRandomApis() throws ParseException {
        String apis = 
    		"UNA:+.? '" +
			"UNB+UNOA:1+SAMPLAIR:NZ+USCS:US+020131:2359+0201312359++CEDIPAX+A+++0'" +
			"UNG+PAXLST+SAMPLECOMM:NZ+TECS:US+020131:2359+0201312359+NZ+001:000'" +
			"UNH+0201312359+PAXLST:001:000:NZ+SA812/020131/2300'" +
			"CTA+IC+:FRED? SAMPLES+1-703-644-5200:TE+1-703-566-8224:FX'" +
			"TDT++SA812+40+SA'" +
			"LOC+005+NZAKL:50'" +
			"DTM+136+1030+M05'" +
			"LOC+008+USLAX:50'" +
			"DTM+132+2200+M05'" +
			"UNS+D'" +
			"PDT+P/182345345:990909:US+MANGUS:SIMON:P:590429:M+PAX+NZAKL:USLAX'" +
			"PDT+P/122222345:970902:US+FEFE:THEODORE:C:560704:U+PAX+NZAKL:USLAX'" +
			"PDT+P/C76543D:920429:GB+BOYLE:ALVIN::521221:U+PAX+NZAKL:USLAX'" +
			"PDT+P/E54321A:980831:IE+O?'LEARY:KRIS:ANN:331231:F+PAX+NZAKL:USLAX'" +
			"UNT+000011+0201312359'" +
			"UNE+1+0201312359'" +
			"UNZ+1+0201312359'";
        
        ApisMessageVo vo = parser.parse(apis);
        List<FlightVo> flights = vo.getFlights();
        assertEquals(2, flights.size());
        System.out.println(vo);
    }
}