package cn.zhengjunren.myblog.system.task;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONUtil;
import cn.zhengjunren.myblog.system.domain.Server;
import cn.zhengjunren.myblog.system.utils.ServerUtil;
import cn.zhengjunren.myblog.system.vo.sever.ServerVO;
import cn.zhengjunren.myblog.common.consts.Consts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: ServerTask</p>
 * <p>Description: </p>
 *
 * @author ZhengJunren
 * @version 1.0.0
 * @date 2019/12/21 18:08
 */
@Slf4j
@Component
public class ServerTask {
    private final SimpMessagingTemplate wsTemplate;

    public ServerTask(SimpMessagingTemplate wsTemplate) {
        this.wsTemplate = wsTemplate;
    }

    /**
     * 按照标准时间来算，每隔 2s 执行一次
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void websocket() throws Exception {
//        log.info("【推送消息】开始执行：{}", DateUtil.formatDateTime(new Date()));
        // 查询服务器状态
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        Dict dict = ServerUtil.wrapServerDict(serverVO);
        wsTemplate.convertAndSend(Consts.PUSH_SERVER, JSONUtil.toJsonStr(dict));
//        log.info("【推送消息】执行结束：{}", DateUtil.formatDateTime(new Date()));
    }
}
