package com.bms.bms_server.service;

import com.bms.bms_server.dto.Agent.AgentRequestDTO;
import com.bms.bms_server.dto.Agent.AgentResponseDTO;
import com.bms.bms_server.entity.Agent;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Route;
import com.bms.bms_server.mapper.AgentMapper;
import com.bms.bms_server.mapper.RouteMapper;
import com.bms.bms_server.repository.AgentRepository;
import com.bms.bms_server.repository.CompanyRepository;
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
    public AgentResponseDTO createAgent(AgentRequestDTO dto) {
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

    public List<AgentResponseDTO> getListAgentByCompanyId(Long companyId) {
        List<Agent> agents = agentRepository.findByCompanyId(companyId);
        return agents.stream().map(AgentMapper::toDTO).collect(Collectors.toList());
    }

    public AgentResponseDTO updateAgent(Long agentId, AgentRequestDTO updatedData) {
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
