package com.fobgochod.system.rpc.handler;

import com.fobgochod.system.rpc.protocol.RpcContent;
import com.fobgochod.system.rpc.util.Constants;
import com.fobgochod.system.rpc.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * HttpServletRpcHandler.java
 *
 * @author fobgochod
 * @date 2021/8/9 17:03
 */
public class ServerServletHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectInputStream oin = new ObjectInputStream(req.getInputStream());
            RpcContent rpcContent = (RpcContent) oin.readObject();
            byte[] contentByte = ObjectUtils.doInvoke(rpcContent);

            resp.setHeader(Constants.HTTP_REQUEST_ID, req.getHeader(Constants.HTTP_REQUEST_ID));
            ServletOutputStream out = resp.getOutputStream();
            out.write(contentByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
