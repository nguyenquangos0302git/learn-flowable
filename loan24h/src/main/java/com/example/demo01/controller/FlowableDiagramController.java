package com.example.demo01.controller;

import com.example.demo01.service.IFlowableDiagramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/flowable")
@RequiredArgsConstructor
public class FlowableDiagramController {

    private final IFlowableDiagramService diagramService;

    @GetMapping("/process/{processInstanceId}/diagram")
    public ResponseEntity<byte[]> diagram(
            @PathVariable String processInstanceId)
            throws IOException {

        byte[] image =
                diagramService.generateDiagram(processInstanceId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(image);
    }

}
