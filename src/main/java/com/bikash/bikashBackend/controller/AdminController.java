package com.bikash.bikashBackend.controller;

import com.bikash.bikashBackend.Service.AgentService;
import com.bikash.bikashBackend.Service.AuthService;
import com.bikash.bikashBackend.Service.RechargeService;
import com.bikash.bikashBackend.View.Response;
import com.bikash.bikashBackend.annotation.ApiController;
import com.bikash.bikashBackend.annotation.IsAdmin;
import com.bikash.bikashBackend.annotation.ValidateData;
import com.bikash.bikashBackend.dto.RechargeDto;
import com.bikash.bikashBackend.dto.UserDto;
import com.bikash.bikashBackend.util.UrlConstraint;
import com.bikash.bikashBackend.util.UseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.AdminManagement.ROOT)
public class AdminController {
    private final AuthService authService;
    private final AgentService agentService;
    private final RechargeService rechargeService;

    @Autowired
    public AdminController(AuthService authService, AgentService agentService, RechargeService rechargeService) {
        this.authService = authService;
        this.agentService = agentService;
        this.rechargeService = rechargeService;
    }

    @PostMapping(UrlConstraint.MERCHANT + UrlConstraint.MerchantManagement.CREATE)
    @IsAdmin
    @ValidateData
    public Response createMerchant(@RequestBody @Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        return authService.createMerchantAccount(userDto, bindingResult, request);
    }

    @PostMapping(UrlConstraint.AGENT + UrlConstraint.AgentManagement.CREATE)
    @ValidateData
    @IsAdmin
    public Response createAgent(@RequestBody @Valid UserDto userDto, BindingResult result, HttpServletRequest request) {
        return agentService.createAgent(userDto, request);
    }

    @IsAdmin
    @ValidateData
    @PostMapping(UrlConstraint.RECHARGE + UrlConstraint.AGENT)
    public Response rechargeToAgent(@RequestBody @Valid RechargeDto rechargeDto, BindingResult result, HttpServletRequest request) {
        return rechargeService.recharge(rechargeDto, request, UseUtil.CREDIT);
    }
}
