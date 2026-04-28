package com.example.loan24h_flowable_service.api;

import com.example.loan24h_flowable_service.payload.request.LoanProposalRequest;
import com.example.loan24h_flowable_service.payload.request.ProposalClaimRequest;
import com.example.loan24h_flowable_service.payload.response.BpmnDeployResponse;
import com.example.loan24h_flowable_service.payload.response.ProposalClaimResponse;
import com.example.loan24h_flowable_service.payload.response.StartWorkflowResponse;
import com.example.loan24h_flowable_service.payload.response.common.DataResponse;
import com.example.loan24h_flowable_service.service.IBpmnDiagramService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/bpmn")
@AllArgsConstructor
public class BpmnDiagramApi {

    private final IBpmnDiagramService iBpmnDiagramService;

    @PostMapping("/deploy")
    public DataResponse<BpmnDeployResponse> deploy(@RequestParam("file") MultipartFile file) throws IOException {
        DataResponse<BpmnDeployResponse> response = new DataResponse<>();
        response.setData(iBpmnDiagramService.deploy(file));
        return response;
    }

    @PostMapping("/start")
    public DataResponse<StartWorkflowResponse> start(@RequestBody LoanProposalRequest loanProposalRequest) {
        DataResponse<StartWorkflowResponse> response = new DataResponse<>();
        response.setData(iBpmnDiagramService.start(loanProposalRequest));
        return response;
    }

    @GetMapping("/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> diagram(
            @PathVariable String processInstanceId)
            throws IOException {


        byte[] image = iBpmnDiagramService.generateDiagram(processInstanceId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image);
    }

}
