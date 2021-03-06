/*
 * All GTAS code is Copyright 2016, The Department of Homeland Security (DHS), U.S. Customs and Border Protection (CBP).
 *
 * Please see LICENSE.txt for details.
 */
package gov.gtas.controller;

import gov.gtas.model.Case;
import gov.gtas.model.lookup.HitDispositionStatus;
import gov.gtas.model.lookup.RuleCat;
import gov.gtas.model.udr.Rule;
import gov.gtas.services.CaseDispositionService;
import gov.gtas.services.RuleCatService;
import gov.gtas.services.dto.CasePageDto;
import gov.gtas.services.dto.CaseRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CaseDispositionController {

    @Autowired
    private CaseDispositionService caseDispositionService;

    @Autowired
    private RuleCatService ruleCatService;

    @RequestMapping(value = "/getAllCaseDispositions", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    CasePageDto getAll(@RequestBody CaseRequestDto request, HttpServletRequest hsr) {
        hsr.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT",
                SecurityContextHolder.getContext());
        return caseDispositionService.findAll(request);
    }

    //getOneHistDisp
    @RequestMapping(method = RequestMethod.POST, value = "/getOneHistDisp",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Case getOneHistDisp(@RequestBody CaseRequestDto request, HttpServletRequest hsr)
            throws ParseException {
        HashMap _tempMap = new HashMap();

        return caseDispositionService.findHitsDispositionByCriteria(request);
    }


    //getHistDispComments
    @RequestMapping(method = RequestMethod.GET, value = "/getHistDispComments")
    public Map<String, Object> getHistDispComments(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate)
            throws ParseException {
        HashMap _tempMap = new HashMap();

        return _tempMap;
    }

    //getRuleCats
    @RequestMapping(method = RequestMethod.GET, value = "/getRuleCats")
    public List<RuleCat> getRuleCats()
            throws Exception {

        List<RuleCat> _tempRuleCatList = new ArrayList<RuleCat>();
        Iterable<RuleCat> _tempIterable =  ruleCatService.findAll();
        if(_tempIterable!=null){
            _tempRuleCatList = StreamSupport.stream(_tempIterable.spliterator(),false).collect(Collectors.toList());
        }

        return _tempRuleCatList;
    }

    //updateHistDisp
    @RequestMapping(method = RequestMethod.POST, value = "/updateHistDisp")
    public
    @ResponseBody
    Case updateHistDisp(@RequestBody CaseRequestDto request, HttpServletRequest hsr) {
        Case aCase = new Case();
        try {
            aCase = caseDispositionService.addCaseComments(request.getFlightId(), request.getPaxId(),
                                                            request.getHitId(), request.getCaseComments(),
                                                                request.getStatus(), request.getValidHit());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return aCase;
    }

    //updateCase
    @RequestMapping(method = RequestMethod.POST, value = "/updateCase")
    public Map<String, Object> updateCase(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate)
            throws ParseException {
        HashMap _tempMap = new HashMap();

        return _tempMap;
    }

    @RequestMapping(value = "/hitdispositionstatuses", method = RequestMethod.GET)
    public @ResponseBody List<HitDispositionStatus> getHitDispositionStatuses() {
        return caseDispositionService.getHitDispositionStatuses();
    }

}
