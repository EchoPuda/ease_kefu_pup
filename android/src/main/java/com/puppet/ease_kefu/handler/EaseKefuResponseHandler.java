package com.puppet.ease_kefu.handler;

import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.model.MessageHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

/**
 * @author Puppet
 */
public class EaseKefuResponseHandler {

    private static MethodChannel channel = null;

    public static void setMethodChannel(MethodChannel channel) {
        EaseKefuResponseHandler.channel = channel;
    }

    /**
     * 登录回调
     */
    public static void onLoginProcess(int process, String status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("process", process);
        map.put("status", status);
        channel.invokeMethod("onLoginProcess",map);
    }

    /**
     * 登出回调
     */
    public static void onLoginOutProcess(int process, String status) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("process", process);
        map.put("status", status);
        channel.invokeMethod("onLoginOutProcess",map);
    }

    /**
     * 网络监听
     */
    public static void onConnectionListener(int status) {
        channel.invokeMethod("onConnectionListener",status);
    }

    /**
     * 消息接收发送监听
     * 1 接收，0 发送
     */
    public static void onMessageStatusListener(int status) {
        channel.invokeMethod("onMessageStatusListener",status);
    }

    /**
     * 消息监听
     */
    public static void onMessageReceived(List<Message> messages){
        ArrayList<Map<String, Object>> msgList = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Message.Type type = messages.get(i).getType();
            switch (type) {
                case TXT:
                    MessageHelper.ExtMsgType extMsgType = MessageHelper.getMessageExtType(messages.get(i));
                    if (extMsgType == MessageHelper.ExtMsgType.GeneralMsg) {
                        map.put("type","TXT");
                        EMTextMessageBody textBody = (EMTextMessageBody) messages.get(i).body();
                        map.put("body",textBody.getMessage());
                    } else if (extMsgType == MessageHelper.ExtMsgType.ToCustomServiceMsg) {
                        map.put("type","CustomServiceMsg");
                        EMTextMessageBody textBody = (EMTextMessageBody) messages.get(i).body();
                        map.put("body",textBody.getMessage());
                    }

                    break;
                case IMAGE:
                    map.put("type","IMAGE");
                    EMImageMessageBody imgBody = (EMImageMessageBody) messages.get(i).body();
                    map.put("body",imgBody.getRemoteUrl());
                    map.put("image",imgBody.getThumbnailUrl());
                    break;
                case VOICE:
                    map.put("type","VOICE");
                    EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) messages.get(i).body();
                    map.put("soundLength",voiceBody.getLength());
                    map.put("body",voiceBody.getRemoteUrl());
                    break;
                case CMD:
                    map.put("type","CMD");
                    break;
                case FILE:
                    map.put("type","FILE");
                    break;
                case VIDEO:
                    map.put("type","VIDEO");
                    break;
                case LOCATION:
                    map.put("type","LOCATION");
                    break;
                default:
                    break;
            }


            String extra = messages.get(i).getStringAttribute("extra","");
            if (!extra.isEmpty()) {
                map.put("extra",extra);
            }
            String msgId = messages.get(i).messageId();
            map.put("msgId",msgId);
            String fromUser = messages.get(i).from();
            map.put("fromUser",fromUser);
            String toUser = messages.get(i).to();
            map.put("toUser",toUser);
            map.put("time",messages.get(i).messageTime());
            msgList.add(map);
            channel.invokeMethod("kefuMsgForListener", msgList);
        }

    }

    /**
     * CMD消息监听
     */
    public static void onCmdMessageReceived(List<Message> messages){
        ArrayList<Map<String, Object>> msgList = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            Message.Type type = messages.get(i).getType();
            switch (type) {
                case TXT:
                    map.put("type","TXT");
                    EMTextMessageBody textBody = (EMTextMessageBody) messages.get(i).body();
                    map.put("body",textBody.getMessage());
                    break;
                case IMAGE:
                    map.put("type","IMAGE");
                    EMImageMessageBody imgBody = (EMImageMessageBody) messages.get(i).body();
                    map.put("body",imgBody.getRemoteUrl());
                    map.put("image",imgBody.getThumbnailUrl());
                    break;
                case VOICE:
                    map.put("type","VOICE");
                    EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) messages.get(i).body();
                    map.put("soundLength",voiceBody.getLength());
                    map.put("body",voiceBody.getRemoteUrl());
                    break;
                case CMD:
                    map.put("type","CMD");
                    EMCmdMessageBody cmdMessageBody = (EMCmdMessageBody) messages.get(i).body();
                    map.put("body",cmdMessageBody.action());
                    break;
                case FILE:
                    map.put("type","FILE");
                    break;
                case VIDEO:
                    map.put("type","VIDEO");
                    break;
                case LOCATION:
                    map.put("type","LOCATION");
                    break;
                default:
                    break;
            }
            String extra = messages.get(i).getStringAttribute("extra","");
            if (!extra.isEmpty()) {
                map.put("extra",extra);
            }
            String msgId = messages.get(i).messageId();
            map.put("msgId",msgId);
            String fromUser = messages.get(i).from();
            map.put("fromUser",fromUser);
            String toUser = messages.get(i).to();
            map.put("toUser",toUser);
            map.put("time",messages.get(i).messageTime());
            msgList.add(map);
            channel.invokeMethod("kefuCmdMsgForListener", msgList);
        }

    }

}
