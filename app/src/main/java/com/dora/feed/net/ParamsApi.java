package com.dora.feed.net;import com.dora.feed.widget.secret.SecurityConfig;import com.famlink.frame.mvp.bean.UpdateVersionBean;import com.famlink.frame.net.biz.PostMethodRequest;import com.famlink.frame.util.AndroidUtils;import android.provider.Settings;import android.text.TextUtils;import com.famlink.frame.mvp.bean.BaseResult;import com.famlink.frame.mvp.bean.BaseResult;import com.famlink.frame.net.biz.GetMethodRequest;import com.famlink.frame.net.biz.Request;import com.famlink.frame.util.AndroidUtils;import com.famlink.frame.util.AppUtils;import com.famlink.frame.util.CacheUtils;import com.famlink.frame.util.JSONUtils;import com.famlink.frame.util.LocalContents;import com.famlink.frame.util.NetUtils;import com.famlink.frame.util.StringUtil;import com.famlink.frame.util.SysoutUtil;import com.famlink.frame.util.Utils;import com.sina.weibo.sdk.openapi.models.Comment;import com.dora.feed.mvp.bean.CommentBean;import com.dora.feed.mvp.bean.CommentDetailBean;import com.dora.feed.mvp.bean.FaoriteBean;import com.dora.feed.mvp.bean.InitApiBean;import com.dora.feed.mvp.bean.MessageBean;import com.dora.feed.mvp.bean.RoomBean;import com.dora.feed.mvp.bean.IndexTitleBean;import com.dora.feed.mvp.bean.IndexItemBean;import com.dora.feed.mvp.bean.SearchBeanTip;import com.dora.feed.mvp.bean.SearchIndexBean;import org.xutils.common.Callback;import org.xutils.http.RequestParams;import org.xutils.x;import java.io.UnsupportedEncodingException;import java.net.URLEncoder;/** * Created by wangkai on 16/6/24. */public class ParamsApi extends com.famlink.frame.net.ParamsApi {    /**     * 公共参数     *     * @return     */    private final static String[] getPublicParam(boolean isFirstinitApi) {        String deviceId = AndroidUtils.getAndroidId(Utils.getContext());                            //获取设备的唯一ID        String userId = CacheUtils.getInstance().getString(LocalContents.USER_ID);                  //用户ID        String deviceModel = AndroidUtils.getDeviceModel();                                         //获取设备型号(Nexus5)        String getScreemesolution = CacheUtils.getInstance().getString(LocalContents.SCREEM_W_H);   //获取屏幕分辨率        int netWork = NetUtils.getNetworkType();                                                    //获取网络类型        ////////////////////////////防反作弊-----begin//////////////////////////////        String IMSI =  CacheUtils.getInstance().getString(LocalContents.IMSI); //国际移动用户识别码        String PHONE_NUMBER =  CacheUtils.getInstance().getString(LocalContents.PHONE_NUMBER); //获取SIM卡手机号        String IMEI = CacheUtils.getInstance().getString(LocalContents.IMEI); //获取IMEI号        String serial = android.os.Build.SERIAL;  //序列号   真机序列号:eg(4df78680771b117b,模拟器:unknown)//        String wifiMac = NetUtils.wifiMac();   //获取mac地址,        String operation = IMEI;                                                                    //获取运营商信息, 包括(IMEI, IMSI, phoneNumber)        ////////////////////////////防反作弊-----end//////////////////////////////        String platom = "0";                                                                        //系统平台   0=android,1=ios        String phoneSystem = android.os.Build.VERSION.RELEASE;                                      //获取手机系统        int versonCode = AndroidUtils.getVersionCode(Utils.getContext());                           //获取版本号        String token = null;                                                                        //加密的token内部校验 (需要URLEncoder.encode)        try {            token = URLEncoder.encode(SecurityConfig.encrypt(deviceId + System.currentTimeMillis()), "utf-8");        } catch (UnsupportedEncodingException e) {            e.printStackTrace();        }        String deviceToken = CacheUtils.getInstance().getString(LocalContents.UMENG_DEVICE_TOKEN);  //友盟的设备token        int chanel_id = AppUtils.getChanel_id(Utils.getContext());                                  //渠道号        userId = CacheUtils.getInstance().getString(LocalContents.USER_ID, "");        if (TextUtils.isEmpty(userId)) {            if (!isFirstinitApi) {                if(!TextUtils.isEmpty(deviceToken)) {                    initApi();                }            }        }        String[] stringMsg = {deviceId, userId, deviceModel, getScreemesolution, netWork + "",                platom, phoneSystem, versonCode + "", token, deviceToken, chanel_id + "", operation + "", IMSI, PHONE_NUMBER, serial};        return stringMsg;    }    /**     *   * 推荐接口     *     * @param ids  //当前获取到得所有的ID     * @param type      //判断是上拉还是下拉 0:下拉 1:上拉     * @param category //分类     * @param openStar //0/1是否获取星座运势    0不获取， 1获取     * @param starName //星座名称     * @param click_ids  //点击的item的ID     * @return     */    public final static Request<IndexItemBean> getIndexItemUrl(String ids,  String category, String type, String openStar, String starName, String click_ids) {        return new GetMethodRequest<IndexItemBean>(IndexItemBean.class, Api.API + Api.RECOMMEND_URL)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])//                .addArgument("ids", ids)                .addArgument("category", category)                .addArgument("type", type)                .addArgument("openStar", openStar)                .addArgument("starName", starName)                .addArgument("click_ids", click_ids);    }    /**     *   * 相关推荐     *     * @param click_id //点击的当前Id     * @return     */    public final static Request<IndexItemBean> getAboutRecommendUrl(String click_id) {        return new GetMethodRequest<IndexItemBean>(IndexItemBean.class, Api.API + Api.ABOUT_RECOMMEND)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])//                .addArgument("ids", ids)                .addArgument("click_id", click_id);    }    /**     * 获取分类中的标题Title内容     * @return     */    public final static Request<IndexTitleBean> requestTitleViewpager() {        return new GetMethodRequest<IndexTitleBean>(IndexTitleBean.class, Api.API + Api.TAB_NAME);    }    /**     * 获取我的评论信息     *     * @return     */    public final static Request<CommentBean> requestComment(int page, String user_id) {        return new GetMethodRequest<CommentBean>(CommentBean.class, Api.API + Api.MYCOMMENTS)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("page", page)                .addArgument("id", user_id);    }    /**     * 获取我的评论信息     *     * @return     */    public final static Request<MessageBean> requestMessage(int page, String user_id) {        return new GetMethodRequest<MessageBean>(MessageBean.class, Api.API + Api.MYMESSAGE)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("page", page)                .addArgument("id", user_id);    }    /**     * 获取我的收藏信息     *     * @return     */    public final static Request<FaoriteBean> requestFavorite(int page, String user_id) {        return new GetMethodRequest<FaoriteBean>(FaoriteBean.class, Api.API + Api.MYFAVART)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("page", page)                .addArgument("id", user_id);    }    /**     * 详情页点击收藏按钮操作     *     * @param user_id     * @param article_id     * @param status     * @return     */    public final static Request<BaseResult> requestFavirateClick(String user_id, String article_id, int status, String trace_id) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.FAVIRATE_CLICK)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("trace_id", trace_id)//                .addArgument("user_id", user_id)                .addArgument("article_id", article_id)                .addArgument("status", status);    }    /************************************************************************************详情界面的所有接口***********start*************************************************************************/    /**     * 获取详情页我的评论信息     *     * @return     */    public final static Request<CommentDetailBean> requestCommentDetail(int page, int id, String trace_id, float bhv_amt) {        return new GetMethodRequest<CommentDetailBean>(CommentDetailBean.class, Api.API + Api.DETAIL_COMMENT)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("trace_id", trace_id)                .addArgument("page", page)                .addArgument("id", id);    }    /**     * 评论某条文章或者是回复某个人发的评论     *     * @param user_id     * @param article_id     * @param content     * @param to_discuss_id     * @param to_user_id     * @param to_user_name     * @return     */    public final static Request<BaseResult> requestCommentForContentOrPerson(String user_id, String article_id, String content, String to_discuss_id, String to_user_id, String to_user_name, String trace_id) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.ADDCOMM)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("trace_id", trace_id)//                .addArgument("user_id",user_id)                .addArgument("article_id", article_id)                .addArgument("content", content)                .addArgument("to_discuss_id", to_discuss_id)                .addArgument("to_user_id", to_user_id)                .addArgument("to_user_name", to_user_name);    }    /**     * 设备注册     *     * @return     */    public final static void initApi() {        StringBuilder sb = new StringBuilder();        String deviceId = AndroidUtils.getAndroidId(Utils.getContext());        int chanel_id = AppUtils.getChanel_id(Utils.getContext());        RequestParams entity = new RequestParams(Api.API + Api.INIT_API);//        entity.addParameter("device_id", deviceId);//        entity.addParameter("chanel_id", chanel_id);//        entity.addParameter("platom",0);  //系统平台0=android,1=ios        entity.addParameter("deviceId", getPublicParam(true)[0]);        entity.addParameter("device", getPublicParam(true)[2]);        entity.addParameter("pro", getPublicParam(true)[3]);        entity.addParameter("netWork", getPublicParam(true)[4]);        entity.addParameter("platom", getPublicParam(true)[5]);        entity.addParameter("system", getPublicParam(true)[6]);        entity.addParameter("version", getPublicParam(true)[7]);        entity.addParameter("token", getPublicParam(true)[8]);        entity.addParameter("deviceToken", getPublicParam(true)[9]);        entity.addParameter("chanel_id", getPublicParam(true)[10]);        entity.addParameter("operation", getPublicParam(true)[11]);        entity.addParameter("imsi", getPublicParam(true)[12]);        entity.addParameter("phoneNumber", getPublicParam(true)[13]);        entity.addParameter("serial", getPublicParam(true)[14]);        entity.addParameter("userId", "");        sb.append(Api.API + Api.INIT_API + "?");        sb.append("deviceId").append("=").append(getPublicParam(true)[0]).append("&").append("device").append("=").append(getPublicParam(true)[2]).append("&").append("netWork").append("=").append(getPublicParam(true)[4])                .append("&").append("platom").append("=").append(getPublicParam(true)[5]).append("&").append("system").append("=").append(getPublicParam(true)[6]).append("&").append("version").append("=").append(getPublicParam(true)[7])                .append("&").append("token").append("=").append(getPublicParam(true)[8]).append("&").append("deviceToken").append("=").append(getPublicParam(true)[9]).append("&").append("chanel_id").append("=").append(getPublicParam(true)[10])                .append("&").append("operation").append("=").append(getPublicParam(true)[11]).append("&").append("pro").append("=").append(getPublicParam(true)[3]).append("&").append("userId").append("=").append("");        System.out.println("url===========" + sb.toString());        x.http().get(entity, new Callback.CommonCallback<String>() {            @Override            public void onSuccess(String result) {                InitApiBean initApiBean = JSONUtils.fromJsonString(result, InitApiBean.class);                if (Integer.parseInt(initApiBean.getmCode()) == 200 || Integer.parseInt(initApiBean.getmCode()) == 201) {                    CacheUtils.getInstance().putString(LocalContents.USER_ID, initApiBean.getData().getId());//注册成功之后返回的userid                }            }            @Override            public void onError(Throwable ex, boolean isOnCallback) {                System.out.println("------------onError:");            }            @Override            public void onCancelled(CancelledException cex) {                System.out.println("onCancelled");            }            @Override            public void onFinished() {                System.out.println("onFinished");            }        });    }    /**     * 评论详情页点赞     *     * @param id     * @param type     * @return     */    public final static Request<BaseResult> requestLikeForContent(String id, int type, String trace_id) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.COMMEND)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("trace_id", trace_id)                .addArgument("id", id)                .addArgument("type", type);    }    /**     * 第三方登录成功之后请求自己服务器     *     * @param ids       第三方平台的id     * @param head_icon 第三方头像     * @param nick      第三方登录的昵称     * @param from      wechat,qq,weibo     * @return     */    public final static Request<BaseResult> requestLogin(String ids, String head_icon, String nick, String from) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.LOGIN)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("ids", ids)                .addArgument("userId", getPublicParam(false)[1])                .addArgument("head_icon", head_icon)                .addArgument("nick", nick)                .addArgument("from", from);    }    /**     * 搜索tip     *     * @param keyword 关键字     * @return     */    public final static Request<SearchBeanTip> requestSearchTip(String keyword) {        return new GetMethodRequest<SearchBeanTip>(SearchBeanTip.class, Api.API + Api.SEARCH_TIP)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("keyword", keyword);    }    /**     * 搜索     *     * @param keyword 关键字     * @param page    当前的页数     * @return     */    public final static Request<SearchIndexBean> requestSearchIndex(String keyword, int page) {        return new GetMethodRequest<SearchIndexBean>(SearchIndexBean.class, Api.API + Api.SEARCH_INDEX)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("keyword", keyword)                .addArgument("page", page);    }    /**     * 检查版本更新     *     * @return     */    public final static Request<UpdateVersionBean> updateVersion() {        return new GetMethodRequest<UpdateVersionBean>(UpdateVersionBean.class, Api.API + Api.UPDATE_URL)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14]);    }    /**     * 根据ids上传trace_id     *     * @return     */    public final static Request<UpdateVersionBean> updateShow(String ids, String trace_id) {        return new GetMethodRequest<UpdateVersionBean>(UpdateVersionBean.class, Api.API + Api.SHARE_IDS_TRACE_ID)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("ids", ids)                .addArgument("trace_id", trace_id);    }    /**     * webView请求的接口和分享的接口     * @param url     * @return     */    public static String webViewUrlAndShareUrl(String url) {        String urlReturn = url + "&deviceId=" + getPublicParam(false)[0] + "&userId=" + getPublicParam(false)[1]                + "&device=" + getPublicParam(false)[2] + "&pro=" + getPublicParam(false)[3] + "&netWork=" + getPublicParam(false)[4]                + "&platom=" + getPublicParam(false)[5] + "&system=" + getPublicParam(false)[6] + "&version=" + getPublicParam(false)[7]                + "&token=" + getPublicParam(false)[8]+"&deviceToken="+ getPublicParam(false)[9]+"&chanel_id="+ getPublicParam(false)[10]                + "@imsi" + getPublicParam(false)[12] + "@phoneNumber" + getPublicParam(false)[13] + "@serial" + getPublicParam(false)[14];        SysoutUtil.out("-------webViewUrlAndShareUrl---------" + urlReturn);        return urlReturn;    }    /**     * 进入详情和退出详情的时间戳     * @return     */    public final static Request<BaseResult> logoutDetail(String articleId, String bhv_amt) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.LOGOUT_TIME)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("bhv_amt", bhv_amt)                .addArgument("article_id", articleId);    }    /**     * 反馈信息接口     * @param content     * @param contact     * @return     */    public final static Request<BaseResult> toRetroaction(String content, String contact) {        return new GetMethodRequest<BaseResult>(BaseResult.class, Api.API + Api.FEED_BACK)                .addArgument("deviceId", getPublicParam(false)[0])                .addArgument("userId", getPublicParam(false)[1])                .addArgument("device", getPublicParam(false)[2])                .addArgument("pro", getPublicParam(false)[3])                .addArgument("netWork", getPublicParam(false)[4])                .addArgument("platom", getPublicParam(false)[5])                .addArgument("system", getPublicParam(false)[6])                .addArgument("version", getPublicParam(false)[7])                .addArgument("token", getPublicParam(false)[8])                .addArgument("deviceToken", getPublicParam(false)[9])                .addArgument("chanel_id", getPublicParam(false)[10])                .addArgument("operation", getPublicParam(false)[11])                .addArgument("imsi", getPublicParam(false)[12])                .addArgument("phoneNumber", getPublicParam(false)[13])                .addArgument("serial", getPublicParam(false)[14])                .addArgument("content", content)//发送内容                .addArgument("contact", contact);//联系方式    }}