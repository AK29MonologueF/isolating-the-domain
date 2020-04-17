package example.presentation.controller.wage;


import example.application.service.contract.ContractRecordService;
import example.application.service.employee.EmployeeQueryService;
import example.domain.model.employee.Employee;
import example.domain.model.employee.EmployeeNumber;
import example.domain.model.contract.BaseHourlyWage;
import example.domain.model.contract.WageCondition;
import example.domain.type.date.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 時給の登録
 */
@Controller
@RequestMapping("wages/{employeeNumber}/register")
class WageRegisterController {

    @Value("${hourly-wage.base}")
    BaseHourlyWage baseHourlyWage;

    EmployeeQueryService employeeQueryService;
    ContractRecordService contractRecordService;

    public WageRegisterController(EmployeeQueryService employeeQueryService, ContractRecordService contractRecordService) {
        this.employeeQueryService = employeeQueryService;
        this.contractRecordService = contractRecordService;
    }

    @ModelAttribute("employee")
    Employee employee(@PathVariable(value = "employeeNumber") EmployeeNumber employeeNumber) {
        return employeeQueryService.choose(employeeNumber);
    }

    @GetMapping
    public String init(Employee employee,
                       Model model) {
        model.addAttribute("baseHourlyWage", baseHourlyWage);
        return "wage/form";
    }

    @PostMapping(value = "confirm")
    public String confirm(Employee employee,
                          @RequestParam("effectiveDate") Date effectiveDate,
                          @RequestParam("baseHourlyWage") BaseHourlyWage baseHourlyWage,
                          Model model) {
        model.addAttribute("effectiveDate", effectiveDate);
        model.addAttribute("baseHourlyWage", baseHourlyWage);
        return "wage/confirm";
    }

    @PostMapping(value = "again")
    public String again(Employee employee,
                        @RequestParam("effectiveDate") Date effectiveDate,
                        @RequestParam("baseHourlyWage") BaseHourlyWage baseHourlyWage,
                        Model model) {
        model.addAttribute("effectiveDate", effectiveDate);
        model.addAttribute("baseHourlyWage", baseHourlyWage);
        return "wage/form";
    }

    @PostMapping(value = "register")
    public String register(Employee employee,
                           @RequestParam("effectiveDate") Date effectiveDate,
                           @RequestParam("baseHourlyWage") BaseHourlyWage baseHourlyWage) {
        WageCondition wageCondition = new WageCondition(baseHourlyWage);
        contractRecordService.registerHourlyWage(employee, effectiveDate, wageCondition);
        return String.format("redirect:/wages/%d/register/completed", employee.employeeNumber().value());
    }

    @GetMapping(value = "completed")
    String showResult(Employee employee) {
        return "wage/result";
    }
}
