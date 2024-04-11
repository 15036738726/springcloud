很简单的demo(主要是熟悉GlobalFilter怎么使用)

在网关处登录之后,生成token,然后postMan中测试时,带上token可以访问到manager服务
如果token是伪造的或者是假的或者没有,都会被网关拦截掉


直接访问manager
http://localhost/manager-center-module/center
{"msg":"未授权","code":401}






