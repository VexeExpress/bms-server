package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Agent.AgentRequestDTO;
import com.bms.bms_server.dto.Agent.AgentResponseDTO;
import com.bms.bms_server.entity.Agent;
import com.bms.bms_server.entity.Company;

public class AgentMapper {
    public static Agent toEntity(AgentRequestDTO dto, Company company) {
        Agent agent = new Agent();
        agent.setCompany(company);
        agent.setName(dto.getName());
        agent.setCode(dto.getCode());
        agent.setPhone(dto.getPhone());
        agent.setNote(dto.getNote());
        agent.setAddress(dto.getAddress());
        agent.setDiscountTicketType(dto.getDiscountTicketType());
        agent.setDiscountTicket(dto.getDiscountTicket());
        agent.setDiscountGoodsType(dto.getDiscountGoodsType());
        agent.setDiscountGoods(dto.getDiscountGoods());
        return agent;
    }
    public static AgentResponseDTO toDTO(Agent agent) {
        AgentResponseDTO dto = new AgentResponseDTO();
        dto.setId(agent.getId());
        dto.setName(agent.getName());
        dto.setCode(agent.getCode());
        dto.setPhone(agent.getPhone());
        dto.setAddress(agent.getAddress());
        dto.setNote(agent.getNote());
        dto.setDiscountTicketType(agent.getDiscountTicketType());
        dto.setDiscountTicket(agent.getDiscountTicket());
        dto.setDiscountGoodsType(agent.getDiscountGoodsType());
        dto.setDiscountGoods(agent.getDiscountGoods());
        dto.setCreatedAt(agent.getCreatedAt());
        return dto;
    }
}
