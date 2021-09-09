package org.kangspace.wework.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * <pre>
 * 企业微信消息
 * touser、toparty、totag不能同时为空，后面不再强调。
 * <a href="https://open.work.weixin.qq.com/api/doc/90001/90143/90372">https://open.work.weixin.qq.com/api/doc/90001/90143/90372</a>
 * </pre>
 * @author kango2gler@gmail.com
 * @version 1.0
 * @date 2021/08/25 23:29
 */
public class WeWorkMessageBean {



    /**
     * 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     * 特殊情况：指定为”@all”，则向该企业应用的全部成员发送
     */
    @JsonProperty("touser")
    private String toUser;
    /**
     * 指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为”@all”时忽略本参数
     */
    @JsonProperty("toparty")
    private String toParty;
    /**
     * 指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
     * 当touser为”@all”时忽略本参数
     */
    @JsonProperty("totag")
    private String toTag;
    /**
     * 消息类型(text,)
     */
    @JsonProperty("msgtype")
    private String msgType;
    /**
     * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
     */
    @JsonProperty("agentid")
    private String agentId;

    /**
     * 	表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
     */
    @JsonProperty("safe")
    private int safe;
    /**
     * 	表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略。
     */
    @JsonProperty("enable_id_trans")
    private int enableIdTrans;
    /**
     * 	表示是否开启重复消息检查，0表示否，1表示是，默认0
     */
    @JsonProperty("enable_duplicate_check")
    private int enableDuplicateCheck;
    /**
     * 	表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
     */
    @JsonProperty("duplicate_check_interval")
    private String duplicateCheckInterval;

    /**
     * 文本
     */
    public static class Text{
        /**
         * 消息内容，最长不超过2048个字节，超过将截断（支持id转译）
         * 其中text参数的content字段可以支持换行、以及A标签，即可打开自定义的网页（可参考以上示例代码）(注意：换行符请用转义过的\n)
         */
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * 图片
     */
    public static class Media{
        @JsonProperty("media_id")
        private String mediaId;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }
    }

    /**
     * 图片
     */
    public static class Image extends Media{
    }

    /**
     * 音频
     */
    public static class Voice extends Media{
    }
    /**
     * 视频
     */
    public static class Video extends Media{
        /**
         * 	视频消息的标题，不超过128个字节，超过会自动截断
         */
        private String title;
        /**
         * 	视频消息的描述，不超过512个字节，超过会自动截断
         */
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * 文件
     */
    public static class File extends Media{
    }


    /**
     * 文本卡片
     * 卡片消息的展现形式非常灵活，支持使用br标签或者空格来进行换行处理，也支持使用div标签来使用不同的字体颜色，
     * 目前内置了3种文字颜色：灰色(gray)、高亮(highlight)、默认黑色(normal)，将其作为div标签的class属性即可，具体用法请参考上面的示例。
     */
    public static class TextCard {
        /**
         * 	标题，不超过128个字节，超过会自动截断
         */
        private String title;
        /**
         * 	描述，不超过512个字节，超过会自动截断
         */
        private String description;
        /**
         * 点击后跳转的链接。最长2048字节，请确保包含了协议头(http/https)
         */
        private String url;
        /**
         * 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。
         */
        @JsonProperty("btntxt")
        private String btnTxt;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBtnTxt() {
            return btnTxt;
        }

        public void setBtnTxt(String btnTxt) {
            this.btnTxt = btnTxt;
        }
    }

    /**
     * 图文消息
     */
    public static class News{
        /**
         * 图文消息，一个图文消息支持1到8条图文
         */
        private List<Article> articles;

        public static class Article{
            /**
             * 标题，不超过128个字节，超过会自动截断（支持id转译）
             */
            private String title;
            /**
             * 描述，不超过512个字节，超过会自动截断（支持id转译）
             */
            private String description;
            /**
             * 点击后跳转的链接。 最长2048字节，请确保包含了协议头(http/https)，小程序或者url必须填写一个
             */
            private String url;
            /**
             * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图 1068*455，小图150*150。
             */
            @JsonProperty("picurl")
            private String picUrl;
            /**
             * 小程序appid，必须是与当前应用关联的小程序，appid和pagepath必须同时填写
             */
            @JsonProperty("appid")
            private String appId;
            /**
             * 点击消息卡片后的小程序页面，仅限本小程序内的页面。appid和pagepath必须同时填写
             */
            @JsonProperty("pagepath")
            private String pagePath;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public String getPagePath() {
                return pagePath;
            }

            public void setPagePath(String pagePath) {
                this.pagePath = pagePath;
            }
        }

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }
    }

    /**
     * 图文消息(mpnews)
     * mpnews类型的图文消息，跟普通的图文消息一致，唯一的差异是图文内容存储在企业微信。
     * 多次发送mpnews，会被认为是不同的图文，阅读、点赞的统计会被分开计算。
     */
    public static class MpNews{
        private List<MpArticle> articles;

        public static class MpArticle{
            /**
             * 	标题，不超过128个字节，超过会自动截断（支持id转译）
             */
            @JsonProperty("title")
            private String title;
            /**
             * 	图文消息缩略图的media_id, 可以通过素材管理接口获得。此处thumb_media_id即上传接口返回的media_id
             */
            @JsonProperty("thumb_media_id")
            private String thumbMediaId;
            /**
             * 	图文消息的作者，不超过64个字节
             */
            @JsonProperty("author")
            private String author;
            /**
             * 	图文消息点击“阅读原文”之后的页面链接
             */
            @JsonProperty("content_source_url")
            private String contentSourceUrl;
            /**
             * 	图文消息的内容，支持html标签，不超过666 K个字节（支持id转译）
             */
            @JsonProperty("content")
            private String content;
            /**
             * 	图文消息的描述，不超过512个字节，超过会自动截断（支持id转译）
             */
            @JsonProperty("digest")
            private String digest;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumbMediaId() {
                return thumbMediaId;
            }

            public void setThumbMediaId(String thumbMediaId) {
                this.thumbMediaId = thumbMediaId;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContentSourceUrl() {
                return contentSourceUrl;
            }

            public void setContentSourceUrl(String contentSourceUrl) {
                this.contentSourceUrl = contentSourceUrl;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }
        }

        public List<MpArticle> getArticles() {
            return articles;
        }

        public void setArticles(List<MpArticle> articles) {
            this.articles = articles;
        }
    }

    /**
     * 目前仅支持markdown语法的子集
     * 微工作台（原企业号）不支持展示markdown消息
     */
    public static class Markdown{
        /**
         * markdown内容，最长不超过2048个字节，必须是utf8编码
         */
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * 小程序通知消息
     * 小程序通知消息只允许绑定了小程序的应用发送，之前，消息会通过统一的会话【小程序通知】发送给用户。
     * 从2019年6月28日起，用户收到的小程序通知会出现在各个独立的应用中。
     * 不支持@all全员发送
     */
    public static class MiniProgramNotice{
        /**
         * 小程序appid，必须是与当前应用关联的小程序
         */
        @JsonProperty("appid")
        private String appId;
        /**
         * 点击消息卡片后的小程序页面，仅限本小程序内的页面。该字段不填则消息点击后不跳转。
         */
        @JsonProperty("page")
        private String page;
        /**
         * 消息标题，长度限制4-12个汉字（支持id转译）
         */
        @JsonProperty("title")
        private String title;
        /**
         * 消息描述，长度限制4-12个汉字（支持id转译）
         */
        @JsonProperty("description")
        private String description;
        /**
         * 是否放大第一个content_item
         */
        @JsonProperty("emphasis_first_item")
        private String emphasisFirstItem;
        /**
         * 消息内容键值对，最多允许10个item
         */
        @JsonProperty("content_item")
        private List<Item> contentItem;

        public static class Item{
            private String key;
            private String value;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEmphasisFirstItem() {
            return emphasisFirstItem;
        }

        public void setEmphasisFirstItem(String emphasisFirstItem) {
            this.emphasisFirstItem = emphasisFirstItem;
        }

        public List<Item> getContentItem() {
            return contentItem;
        }

        public void setContentItem(List<Item> contentItem) {
            this.contentItem = contentItem;
        }
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public int getEnableIdTrans() {
        return enableIdTrans;
    }

    public void setEnableIdTrans(int enableIdTrans) {
        this.enableIdTrans = enableIdTrans;
    }

    public int getEnableDuplicateCheck() {
        return enableDuplicateCheck;
    }

    public void setEnableDuplicateCheck(int enableDuplicateCheck) {
        this.enableDuplicateCheck = enableDuplicateCheck;
    }

    public String getDuplicateCheckInterval() {
        return duplicateCheckInterval;
    }

    public void setDuplicateCheckInterval(String duplicateCheckInterval) {
        this.duplicateCheckInterval = duplicateCheckInterval;
    }

    @JsonProperty("text")
    private Text text;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("voice")
    private Voice voice;
    @JsonProperty("video")
    private Video video;
    @JsonProperty("file")
    private File file;
    @JsonProperty("textcard")
    private TextCard textCard;
    @JsonProperty("news")
    private News news;
    @JsonProperty("mpnews")
    private MpNews mpNews;
    @JsonProperty("markdown")
    private Markdown markdown;
    @JsonProperty("miniprogram_notice")
    private MiniProgramNotice miniProgramNotice;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.msgType = MsgType.TEXT.getType();
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.msgType = MsgType.IMAGE.getType();
        this.image = image;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.msgType = MsgType.VOICE.getType();
        this.voice = voice;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.msgType = MsgType.VIDEO.getType();
        this.video = video;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.msgType = MsgType.FILE.getType();
        this.file = file;
    }

    public TextCard getTextCard() {
        return textCard;
    }

    public void setTextCard(TextCard textCard) {
        this.msgType = MsgType.TEXT_CARD.getType();
        this.textCard = textCard;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.msgType = MsgType.NEWS.getType();
        this.news = news;
    }

    public MpNews getMpNews() {
        return mpNews;
    }

    public void setMpNews(MpNews mpNews) {
        this.msgType = MsgType.MP_NEWS.getType();
        this.mpNews = mpNews;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.msgType = MsgType.MARKDOWN.getType();
        this.markdown = markdown;
    }

    public MiniProgramNotice getMiniProgramNotice() {
        return miniProgramNotice;
    }

    public void setMiniProgramNotice(MiniProgramNotice miniProgramNotice) {
        this.msgType = MsgType.MINI_PROGRAM_NOTICE.getType();
        this.miniProgramNotice = miniProgramNotice;
    }

    /**
     * 消息类型
     */
    public enum MsgType{
        TEXT("text"),
        IMAGE("image"),
        VOICE("voice"),
        VIDEO("video"),
        FILE("file"),
        TEXT_CARD("textcard"),
        NEWS("news"),
        MP_NEWS("mpnews"),
        MARKDOWN("markdown"),
        MINI_PROGRAM_NOTICE("miniprogram_notice"),
        ;

        private String type;
        MsgType(String type){
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
    //TODO 模板卡片消息 及以下消息模式待定义
}
