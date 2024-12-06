package com.bms.bms_server.modules.ModuleAgent.mapper;

import com.bms.bms_server.modules.ModuleAgent.dto.DTO_RQ_Agent;
import com.bms.bms_server.modules.ModuleAgent.dto.DTO_RP_Agent;
import com.bms.bms_server.modules.ModuleAgent.entity.Agent;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;

public class AgentMapper {
    public static Agent toEntity(DTO_RQ_Agent dto, Company company) {
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
    public static DTO_RP_Agent toDTO(Agent agent) {
        DTO_RP_Agent dto = new DTO_RP_Agent();
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
