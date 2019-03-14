package com.tom.cf.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nmis/statistics")
public class BusinessStatisticsApi {

    private final static String SUM_TIME="backend/pages/statistics/sum_time_statistics";

    private final static String SUM_ORG = "backend/pages/statistics/sum_org_statistics";

    private final static String SUM_DOCTOR = "backend/pages/statistics/sum_doctor_statistics";

    private final static String SUM_DRUG = "backend/pages/statistics/sum_drug_statistics";

    private final static String SUM_APPRAISE = "backend/pages/statistics/sum_appraise_statistics";
    @GetMapping("/byOrg")
    public String sumOrgBusiness() {
        return SUM_ORG;
    }

    @GetMapping("/byTime")
    public String byTime() {
        return SUM_TIME;
    }

    @GetMapping("/byDoctor")
    public String byDoctor() {
        return SUM_DOCTOR;
    }

    @GetMapping("/byDrug")
    public String byDrug() {
        return SUM_DRUG;
    }

    @GetMapping("/byAppraise")
    public String byAppraise() {
        return SUM_APPRAISE;
    }
}
