package com.example.demo01.service;

import java.io.IOException;

public interface IFlowableDiagramService {

    byte[] generateDiagram(String processInstanceId) throws IOException;

}
