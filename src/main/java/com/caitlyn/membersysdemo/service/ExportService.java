package com.caitlyn.membersysdemo.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExportService {
    void exportMembers(HttpServletResponse response) throws IOException;
}