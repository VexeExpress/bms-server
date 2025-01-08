package com.bms.bms_server.modules.ModuleAgent.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleAgent.dto.*;
import com.bms.bms_server.modules.ModuleAgent.entity.Agent;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleAgent.mapper.AgentMapper;
import com.bms.bms_server.modules.ModuleAgent.repository.AgentRepository;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleRoute.repository.RouteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AgentService {
    @Autowired
    AgentRepository agentRepository;

    @Autowired
    CompanyRepository companyRepository;
    PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    // PB.05_US.01: Add new agent
    public DTO_RP_Agent createAgent(DTO_RQ_Agent dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_NAME_AGENT);
        }
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USERNAME_AGENT);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_PASSWORD_AGENT);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        boolean existsName = agentRepository.existsByCompanyAndName(company, dto.getName());
        if (existsName) {
            throw new AppException(ErrorCode.AGENT_ALREADY_EXISTED);
        }
        boolean existsUsername = agentRepository.existsByCompanyAndUsername(company, dto.getUsername());
        if (existsUsername) {
            throw new AppException(ErrorCode.USERNAME_AGENT_ALREADY_EXISTED);
        }
        String hashPassword = passwordEncoder.encode(dto.getPassword());
        if (hashPassword == null || hashPassword.isEmpty()) {
            throw new AppException(ErrorCode.PASSWORD_ENCRYPTION_FAILED);
        }
        dto.setPassword(hashPassword);
        Agent agent = AgentMapper.toEntity(dto, company);
        Agent saved = agentRepository.save(agent);
        return AgentMapper.toDTO(saved);
    }

    // PB.05_US.02: Update agent information
    public DTO_RP_Agent updateAgent(Long agentId, DTO_RQ_Agent dto) {
        Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new AppException(ErrorCode.AGENT_NOT_FOUND));
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_NAME_AGENT);
        }
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        agent.setName(dto.getName());
        agent.setStatus(dto.getStatus());
        agent.setCode(dto.getCode());
        agent.setPhone(dto.getPhone());
        agent.setNote(dto.getNote());
        agent.setAddress(dto.getAddress());
        agent.setDiscountTicketType(dto.getDiscountTicketType());
        agent.setDiscountTicket(dto.getDiscountTicket());
        agent.setDiscountGoodsType(dto.getDiscountGoodsType());
        agent.setDiscountGoods(dto.getDiscountGoods());

        Agent updateAgent = agentRepository.save(agent);
        return AgentMapper.toDTO(updateAgent);
    }

    // PB.05_US.03: Remove agent
    public void deleteAgentById(Long agentId) {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        if (agentOptional.isEmpty()) {
            throw new AppException(ErrorCode.AGENT_NOT_FOUND);
        }
        agentRepository.deleteById(agentId);
    }

    // PB.05_US.04: Filter/Get list agent
    public List<DTO_RP_Agent> getListAgentByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Agent> agents = agentRepository.findByCompanyId(companyId);
        return agents.stream()
                .map(AgentMapper::toDTO)
                .collect(Collectors.toList());
    }

    // PB.05_US.05: Filter/Get list agent name
    public List<DTO_RP_AgentName> getListAgentNameByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        List<Agent> agents = agentRepository.findByCompanyId(companyId);
        return agents.stream()
                .filter(Agent::getStatus)
                .map(AgentMapper::dtoRpAgentName)
                .collect(Collectors.toList());
    }

    // PB.05_US.06: Assign agent to specific routes
    @Transactional
    public DTO_RP_AssignAgentToRoute assignAgentToRoutes(Long agentId, DTO_RQ_AssignAgentToRoute dto) throws JsonProcessingException {
        var agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AppException(ErrorCode.AGENT_NOT_FOUND));

        // Chuyển danh sách routeIds sang JSON
        String routeIdsJson = objectMapper.writeValueAsString(dto.getRouteIds());
        agent.setRouteIds(routeIdsJson);

        agentRepository.save(agent);

        DTO_RP_AssignAgentToRoute response = new DTO_RP_AssignAgentToRoute();
        response.setId(agentId);
        response.setRoutes(dto.getRouteIds());
        return response;

    }

    public void enableAgentAccount(Long agentId) {
    }

    public void disableAgentAccount(Long agentId) {
        
    }


    // PB.05_US.07: Filter/Get agent's performance

    // PB.05_US.08: Enable/Disable agent account

    // PB.05_US.09: Notifications for agents


//    public List<DTO_RP_Agent> getListAgentByCompanyId(Long companyId) {
//        List<Agent> agents = agentRepository.findByCompanyId(companyId);
//        return agents.stream().map(AgentMapper::toDTO).collect(Collectors.toList());
//    }




}
