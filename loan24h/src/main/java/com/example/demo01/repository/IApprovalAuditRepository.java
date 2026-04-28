package com.example.demo01.repository;

import com.example.demo01.entity.ApprovalAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApprovalAuditRepository extends JpaRepository<ApprovalAudit, Long> {
}
