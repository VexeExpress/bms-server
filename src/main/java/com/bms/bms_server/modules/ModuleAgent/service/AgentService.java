package com.bms.bms_server.modules.ModuleAgent.service;

import com.bms.bms_server.modules.ModuleAgent.dto.DTO_RQ_Agent;
import com.bms.bms_server.modules.ModuleAgent.dto.DTO_RP_Agent;
import com.bms.bms_server.modules.ModuleAgent.entity.Agent;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleAgent.mapper.AgentMapper;
import com.bms.bms_server.modules.ModuleAgent.repository.AgentRepository;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    CompanyRepository companyRepository;
    public DTO_RP_Agent createAgent(DTO_RQ_Agent dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required.");
        }
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Agent name is required.");
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found."));
        boolean exists = agentRepository.existsByCompanyAndName(company, dto.getName());
        if (exists) {
            throw new IllegalArgumentException("AgentName already exists for this company.");
        }
        Agent agent = AgentMapper.toEntity(dto, company);
        Agent saved = agentRepository.save(agent);
        return AgentMapper.toDTO(saved);
    }

    public List<DTO_RP_Agent> getListAgentByCompanyId(Long companyId) {
        List<Agent> agents = agentRepository.findByCompanyId(companyId);
        return agents.stream().map(AgentMapper::toDTO).collect(Collectors.toList());
    }

    public DTO_RP_Agent updateAgent(Long agentId, DTO_RQ_Agent updatedData) {
        Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new EntityNotFoundException("Agent with ID " + agentId + " not found"));
        if (updatedData.getName() != null) {
            agent.setName(updatedData.getName());
            agent.setCode(updatedData.getCode());
            agent.setPhone(updatedData.getPhone());
            agent.setNote(updatedData.getNote());
            agent.setAddress(updatedData.getAddress());
            agent.setDiscountTicketType(updatedData.getDiscountTicketType());
            agent.setDiscountTicket(updatedData.getDiscountTicket());
            agent.setDiscountGoodsType(updatedData.getDiscountGoodsType());
            agent.setDiscountGoods(updatedData.getDiscountGoods());
        }
        Agent updateAgent = agentRepository.save(agent);
        return AgentMapper.toDTO(updateAgent);
    }

    public void deleteAgentById(Long agentId) {
        Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new EntityNotFoundException("Dai ly không tồn tại"));
        agentRepository.delete(agent);
    }
}
