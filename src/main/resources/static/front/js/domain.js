var MyNamespace = MyNamespace || {};

MyNamespace.Result = function(code,data){
    this.code=code;
    this.data=data;
}

MyNamespace.User = function (id,account,name,password,avatar,email,info,banned,points) {
    this.id=id;
    this.account=account;
    this.name=name;
    this.password=password;
    this.avatar=avatar;
    this.email=email;
    this.info=info;
    this.banned=banned;
    this.points=points;
};

MyNamespace.Article = function (id,title,content,category,user,tag,createAt,updateAt,support,oppose,img,view,reward) {
    this.id=id;
    this.title=title;
    this.content=content;
    this.category=category;//内部类
    this.user=user;//内部类
    this.tag=tag;//内部类
    this.createAt=createAt;
    this.updateAt=updateAt;
    this.support=support;
    this.oppose=oppose;
    this.img=img;
    this.view=view;
    this.reward=reward;
};

MyNamespace.Comment = function (id, lastId, article, user, content, createdAt, support, oppose) {
    this.id = id;
    this.lastId = lastId;
    this.article = article;//内部类
    this.user = user;//内部类
    this.content = content;
    this.createdAt = createdAt;
    this.support = support;
    this.oppose = oppose;
};

MyNamespace.category = function (id, name) {
    this.id = id;
    this.name = name;
};

MyNamespace.Tag = function (id, name) {
    this.id = id;
    this.name = name;
};