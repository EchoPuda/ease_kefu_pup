package com.puppet.ease_kefu.handler;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Conversation;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.callback.ValueCallBack;
import com.hyphenate.helpdesk.model.MessageHelper;
import com.hyphenate.helpdesk.model.ToCustomServiceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

/**
 * @author Puppet
 */
public class EaseKefuHandler {

    private static PluginRegistry.Registrar registrar = null;

    public static void setRegistrar(PluginRegistry.Registrar registrar) {
        EaseKefuHandler.registrar = registrar;
    }

    /**
     * 初始化
     */
    public static void initEaseKefu(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String appKey = call.argument("kAppKey");
        String tenantId = call.argument("kTenantId");
        boolean debug = (boolean)call.argument("debug");
        // 初始化配置
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(appKey);
        options.setAppkey(tenantId);
        if (debug) {
            options.setConsoleLog(true);
        } else {
            options.setConsoleLog(false);
        }
        final boolean success = ChatClient.getInstance().init(registrar.context(), options);
        registrar.activity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                result.success(success);
            }
        });
    }

    public static void register(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String username = call.argument("username");
        String password = call.argument("password");

        ChatClient.getInstance().register(username, password, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("success");
                    }
                });
            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }
        });
    }

    /**
     * 登录（二选一）
     */
    public static void login(MethodCall call, MethodChannel.Result result) {
        // 获取数据
        String username = call.argument("username");
        String password = call.argument("password");

        ChatClient.getInstance().login(username, password, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(1, "success");
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(-1, "error");
                    }
                });
            }

            @Override
            public void onProgress(final int progress, final String status) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(progress, status);
                    }
                });
            }
        });
    }

    /**
     * 登录（二选一）
     */
    public static void loginWithToken(MethodCall call, MethodChannel.Result result) {
        // 获取数据
        String username = call.argument("username");
        String token = call.argument("token");

        ChatClient.getInstance().loginWithToken(username, token, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(1, "success");
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(-1, "error");
                    }
                });
            }

            @Override
            public void onProgress(final int progress, final String status) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginProcess(progress, status);
                    }
                });
            }
        });
    }

    /**
     * 获取登录状态
     */
    public static void isLoggedInBefore(MethodCall call, final MethodChannel.Result result) {
        if(ChatClient.getInstance().isLoggedInBefore()){
            //已经登录，可以直接进入会话界面
            registrar.activity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    result.success(true);
                }
            });
        }else{
            //未登录，需要登录后，再进入会话界面
            registrar.activity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    result.success(false);
                }
            });
        }
    }

    /**
     * 退出
     */
    public static void loginOut(MethodCall call, MethodChannel.Result result) {
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginOutProcess(1, "success");
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginOutProcess(-1, "error");
                    }
                });
            }

            @Override
            public void onProgress(final int progress, final String status) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EaseKefuResponseHandler.onLoginOutProcess(progress, status);
                    }
                });
            }
        });
    }

    /**
     * 绑定联系人
     */
    public static void setServiceImNumber(MethodCall call, MethodChannel.Result result) {
        // 获取数据
        String username = call.argument("username");
        ChatManager.getInstance().bindChat(username);
    }

    /**
     * 添加网络监听
     */
    public static void addConnectionListener(MethodCall call, MethodChannel.Result result) {
        ChatClient.getInstance().addConnectionListener(new ChatClient.ConnectionListener() {
            @Override
            public void onConnected() {
                //成功连接到服务器
                EaseKefuResponseHandler.onConnectionListener(2000);
            }

            @Override
            public void onDisconnected(int errorcode) {
                //errorcode的值
                //Error.USER_REMOVED 账号移除
                //Error.USER_LOGIN_ANOTHER_DEVICE 账号在其他地方登录
                //Error.USER_AUTHENTICATION_FAILED 账号密码错误
                //Error.USER_NOT_FOUND  账号找不到
                EaseKefuResponseHandler.onConnectionListener(errorcode);
            }
        });
    }

    /**
     * 添加消息监听
     */
    public static void addMessageListener(MethodCall call, MethodChannel.Result result) {
        ChatClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    /**
     * 移除接收消息监听
     */
    public static void removeMessageListener(MethodCall call, MethodChannel.Result result) {
        ChatClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    /**
     * 消息监听
     */
    private static ChatManager.MessageListener msgListener = new ChatManager.MessageListener() {
        @Override
        public void onMessage(List<Message> msgs) {
            EaseKefuResponseHandler.onMessageReceived(msgs);
        }

        @Override
        public void onCmdMessage(List<Message> msgs) {
            EaseKefuResponseHandler.onCmdMessageReceived(msgs);
        }

        @Override
        public void onMessageStatusUpdate() {
            EaseKefuResponseHandler.onMessageStatusListener(1);
        }

        @Override
        public void onMessageSent() {
            EaseKefuResponseHandler.onMessageStatusListener(0);
        }
    };

    /**
     * 获取企业欢迎语
     */
    public static void getEnterpriseWelcome(MethodCall call, final MethodChannel.Result result) {
        ChatClient.getInstance().chatManager().getEnterpriseWelcome(new ValueCallBack<String>() {
            @Override
            public void onSuccess(final String value) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(value);
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }
        });
    }

    /**
     * 获取当前会话ID
     * 当返回value为空时，则代表此会话不存在，也就是未咨询或者咨询已结束，下次咨询可先发欢迎语
     * 当返回value不为空时，则返回的当前会话的会话ID，也就是说会话正在咨询中，不需要发送欢迎语
     */
    public static void getCurrentSessionId(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String toChatUsername = call.argument("toChatUsername");
        ChatClient.getInstance().chatManager().getCurrentSessionId(toChatUsername, new ValueCallBack<String>() {
            @Override
            public void onSuccess(final String value) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(value);
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }
        });
    }

    /**
     * 发送文本消息
     */
    public static void createTxtSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String content = call.argument("content");
        String toChatUsername = call.argument("toChatUsername");

        //发送一条文本消息， content 为消息文字内容， toChatUsername为客服设置的IM服务号
        final Message message = Message.createTxtSendMessage(content, toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送扩展消息
     */
    public static void createExtraTxtSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String content = call.argument("content");
        String toChatUsername = call.argument("toChatUsername");
        String extraString = call.argument("extraString");

        //发送一条文本消息， content 为消息文字内容， toChatUsername为客服设置的IM服务号
        final Message message = Message.createTxtSendMessage(content, toChatUsername);
        message.setAttribute("extra",extraString);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送语音消息
     */
    public static void createVoiceSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String filePath = call.argument("filePath");
        int length = (int) call.argument("length");
        String toChatUsername = call.argument("toChatUsername");

        //filePath为语音文件路径， length为录音时间(秒)， toChatUsername为IM服务号
        final Message message = Message.createVoiceSendMessage(filePath, length, toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送图片消息
     */
    public static void createImageSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String filePath = call.argument("filePath");
        boolean originallImage = (boolean)call.argument("originallImage");
        String toChatUsername = call.argument("toChatUsername");

        //filePath为图片路径， false为不发送原图(默认超过100k的图片都会压缩后发给对方)，需要发送
        assert filePath != null;
        final Message message = Message.createImageSendMessage(filePath, originallImage, toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送地理消息
     */
    public static void createLocationSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        double latitude = (double)call.argument("latitude");
        double longitude = (double)call.argument("longitude");
        String locAddress = call.argument("locAddress");
        String toChatUsername = call.argument("toChatUsername");

        //latitude为维度，longitude为经度，locAddress为具体位置内容, toChatUsername为IM服务号
        final Message message = Message.createLocationSendMessage(latitude, longitude, locAddress, toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送文件消息
     */
    public static void createFileSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String filePath = call.argument("filePath");
        String toChatUsername = call.argument("toChatUsername");

        //filePath为本地文件路径，toChatUsername为IM服务号
        assert filePath != null;
        final Message message = Message.createFileSendMessage(filePath, toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送转接客服消息
     */
    public static void createTranferToKefuMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String toUserName = call.argument("toUserName");
        ToCustomServiceInfo info = new ToCustomServiceInfo();

        //toUserName是转接客服消息的Target客服（可以从收到的转接客服按钮消息中提取） ，
        // info是ToCustomServiceInfo的消息实体（收到的转接客服按钮消息内容中提取）
        final Message message = Message.createTranferToKefuMessage(toUserName, info);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 发送透传消息
     */
    public static void createCmdSendMessage(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String action = call.argument("action");
        String toChatUsername = call.argument("toChatUsername");

        //toUserName是转接客服消息的Target客服（可以从收到的转接客服按钮消息中提取） ，
        // info是ToCustomServiceInfo的消息实体（收到的转接客服按钮消息内容中提取）
        final Message message = Message.createSendMessage(Message.Type.CMD);
        message.setBody(new EMCmdMessageBody(action));
        message.setTo(toChatUsername);
        ChatClient.getInstance().chatManager().sendMessage(message, new Callback() {
            @Override
            public void onSuccess() {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success(message.messageId());
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                registrar.activity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.success("error");
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    /**
     * 获取聊天记录
     */
    public static void getAllMessages(MethodCall call, final MethodChannel.Result result) {
        String toChatUsername = call.argument("toChatUsername");
        Conversation conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);

        //获取此会话的所有消息
        if (conversation != null) {
            List<Message> messages = conversation.getAllMessages();
            List<Map<String, Object>> msgList = new ArrayList<>();
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
            }
            result.success(msgList);
        }
    }

    /**
     * 获取更多聊天记录
     */
    public static void getAllMessagesMore(MethodCall call, final MethodChannel.Result result) {
        String toChatUsername = call.argument("toChatUsername");
        String startMsgId = call.argument("startMsgId");
        Conversation conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);

        //获取此会话的所有消息
        if (conversation != null) {
            List<Message> messages = conversation.loadMessages(startMsgId,15);
            List<Map<String, Object>> msgList = new ArrayList<>();
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
            }
            result.success(msgList);
        }
    }

    /**
     * 获取某个会话未读消息数量
     */
    public static void getUnreadMessagesCount(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String toChatUsername = call.argument("toChatUsername");

        int count = ChatClient.getInstance().chatManager().getConversation(toChatUsername).unreadMessagesCount();
        result.success(count);
    }

    /**
     * 获取未读消息数量
     */
    public static void getAllUnreadMessagesCount(MethodCall call, final MethodChannel.Result result) {
        int count = ChatClient.getInstance().chatManager().getUnreadMsgsCount();
        result.success(count);
    }

    /**
     * 某个会话未读消息数清零
     */
    public static void markConversationsAsRead(MethodCall call, final MethodChannel.Result result) {
        // 获取数据
        String toChatUsername = call.argument("toChatUsername");

        Conversation conversation = ChatClient.getInstance().chatManager().getConversation(toChatUsername);
        //指定会话消息未读数清零
        conversation.markAllMessagesAsRead();

    }

    /**
     * 所有未读消息数清零
     */
    public static void markAllConversationsAsRead(MethodCall call, final MethodChannel.Result result) {
        //所有未读消息数清零
        ChatClient.getInstance().chatManager().markAllConversationsAsRead();
    }


}
