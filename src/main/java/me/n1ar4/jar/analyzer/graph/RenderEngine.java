/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.jar.analyzer.graph;

import me.n1ar4.jar.analyzer.entity.MethodResult;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RenderEngine {
    private static final Map<String, MethodResult> methodIdStringMap = new HashMap<>();

    private static String generateId() {
        return UUID.randomUUID().toString();
    }

    private static String getShortClassName(String fullClassName) {
        fullClassName = fullClassName.replace("/", ".");
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }

    public static String processGraph(MethodResult cur,
                                      List<MethodResult> caller,
                                      List<MethodResult> callee) {
        methodIdStringMap.clear();
        MethodData data = new MethodData();
        String curId = generateId();
        data.setUuid(curId);
        data.setMethodString(getShortClassName(cur.getClassName()) + " " + cur.getMethodName());
        List<String> callerIds = new ArrayList<>();
        methodIdStringMap.put(curId, cur);
        for (MethodResult c : caller) {
            String id = generateId();
            methodIdStringMap.put(id, c);
            callerIds.add(id);
        }
        List<String> calleeIds = new ArrayList<>();
        for (MethodResult c : callee) {
            String id = generateId();
            methodIdStringMap.put(id, c);
            calleeIds.add(id);
        }
        data.setCalleeIds(calleeIds);
        data.setCallerIds(callerIds);

        StringBuilder nodesBuffer = new StringBuilder();
        for (Map.Entry<String, MethodResult> entry : methodIdStringMap.entrySet()) {
            String id = entry.getKey();
            MethodResult mr = entry.getValue();
            String temp = String.format("{ id: '%s', name: '%s' },\n", id,
                    getShortClassName(mr.getClassName()) + " " + mr.getMethodName());
            nodesBuffer.append(temp);
        }

        StringBuilder linksBuffer = new StringBuilder();
        for (String callerId : callerIds) {
            String temp = String.format("{ source: '%s', target: '%s' },\n", callerId, curId);
            linksBuffer.append(temp);
        }
        for (String calleeId : calleeIds) {
            String temp = String.format("{ source: '%s', target: '%s' },\n", curId, calleeId);
            linksBuffer.append(temp);
        }

        GraphData graphData = new GraphData();
        graphData.setCurrentNodeId(data.getUuid());
        graphData.setNodes(nodesBuffer.toString());
        graphData.setLinks(linksBuffer.toString());

        String html = HtmlGraphUtil.render(graphData);
        if (html == null) {
            return null;
        }
        try {
            String fileName = String.format("jar-analyzer-graph-%d.html", System.currentTimeMillis());
            Files.write(Paths.get(fileName), html.getBytes());
            return fileName;
        } catch (Exception ignored) {
        }
        return null;
    }
}
