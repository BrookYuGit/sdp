/*!
 *  build: vue-admin-beautiful 
 *  vue-admin-beautiful author: chuzhixin 1204505056@qq.com 
 *  vue-admin-beautiful QQ Group(QQ群): 972435319、1139183756 
 *  time: 2022-6-30 14:34:29
 */
(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["vab-layouts"],{1294:function(e,t,a){},"1e68":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"nav-bar-container"},[a("el-row",{attrs:{gutter:15}},[a("el-col",{attrs:{xs:4,sm:12,md:12,lg:12,xl:12}},[a("div",{staticClass:"left-panel"},[a("i",{staticClass:"fold-unfold",class:e.collapse?"el-icon-s-unfold":"el-icon-s-fold",attrs:{title:e.collapse?"展开":"收起"},on:{click:e.handleCollapse}}),a("vab-breadcrumb",{staticClass:"hidden-xs-only"})],1)]),a("el-col",{attrs:{xs:20,sm:12,md:12,lg:12,xl:12}},[a("div",{staticClass:"right-panel"},[a("vab-theme-bar",{staticClass:"hidden-xs-only"})],1)])],1)],1)},s=[],n=a("1da1"),r=a("5530"),o=(a("96cf"),a("2f62")),l={name:"VabNavBar",data:function(){return{pulse:!1}},computed:Object(r["a"])({},Object(o["c"])({collapse:"settings/collapse",visitedRoutes:"tabsBar/visitedRoutes",device:"settings/device",routes:"routes/routes"})),methods:Object(r["a"])(Object(r["a"])({},Object(o["b"])({changeCollapse:"settings/changeCollapse"})),{},{handleCollapse:function(){this.changeCollapse()},refreshRoute:function(){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:e.$baseEventBus.$emit("reload-router-view"),e.pulse=!0,setTimeout((function(){e.pulse=!1}),1e3);case 3:case"end":return t.stop()}}),t)})))()}})},c=l,u=(a("d137"),a("2877")),d=Object(u["a"])(c,i,s,!1,null,"4ff241b6",null);t["default"]=d.exports},"20a2":function(e,t,a){},"36cc":function(e,t,a){},"397a":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return e.routerView?a("div",{staticClass:"app-main-container"},[e.isOri?a("vab-github-corner"):e._e(),a("transition",{attrs:{mode:"out-in",name:"fade-transform"}},[a("keep-alive",{attrs:{include:e.cachedRoutes,max:e.keepAliveMaxNum}},[a("router-view",{key:e.key,staticClass:"app-main-height"})],1)],1),e.footerCopyright?a("footer",{staticClass:"footer-copyright"},[e._v(" Copyright "),a("vab-icon",{attrs:{icon:["fas","copyright"]}}),e._v(" vue-admin-beautiful-pro 开源免费版 "+e._s(e.fullYear)+" ")],1):e._e()],1):e._e()},s=[],n=a("5530"),r=(a("d3b7"),a("159b"),a("b0c0"),a("2f62")),o=a("f121"),l={name:"VabAppMain",data:function(){return{isOri:o["isOri"],show:!1,fullYear:(new Date).getFullYear(),copyright:o["copyright"],title:o["title"],keepAliveMaxNum:o["keepAliveMaxNum"],routerView:!0,footerCopyright:o["footerCopyright"]}},computed:Object(n["a"])(Object(n["a"])({},Object(r["c"])({visitedRoutes:"tabsBar/visitedRoutes",device:"settings/device"})),{},{cachedRoutes:function(){var e=[];return this.visitedRoutes.forEach((function(t){t.meta.noKeepAlive||e.push(t.name)})),e},key:function(){return this.$route.path}}),watch:{$route:{handler:function(e){"mobile"===this.device&&this.foldSideBar()},immediate:!0}},created:function(){var e=this;this.$baseEventBus.$on("reload-router-view",(function(){e.routerView=!1,e.$nextTick((function(){e.routerView=!0}))}))},mounted:function(){},methods:Object(n["a"])({},Object(r["b"])({foldSideBar:"settings/foldSideBar"}))},c=l,u=(a("db38"),a("2877")),d=Object(u["a"])(c,i,s,!1,null,"7f2c4d1e",null);t["default"]=d.exports},"3ab0":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"vue-admin-beautiful-wrapper",class:e.classObj},["horizontal"===e.layout?a("div",{staticClass:"layout-container-horizontal",class:{fixed:"fixed"===e.header,"no-tabs-bar":"false"===e.tabsBar||!1===e.tabsBar}},[a("div",{class:"fixed"===e.header?"fixed-header":""},[a("vab-top-bar"),"true"===e.tabsBar||!0===e.tabsBar?a("div",{class:{"tag-view-show":e.tabsBar}},[a("div",{staticClass:"vab-main"},[a("vab-tabs-bar")],1)]):e._e()],1),a("div",{staticClass:"vab-main main-padding"},[a("vab-app-main")],1)]):a("div",{staticClass:"layout-container-vertical",class:{fixed:"fixed"===e.header,"no-tabs-bar":"false"===e.tabsBar||!1===e.tabsBar}},["mobile"===e.device&&!1===e.collapse?a("div",{staticClass:"mask",on:{click:e.handleFoldSideBar}}):e._e(),a("vab-side-bar"),a("div",{staticClass:"vab-main",class:e.collapse?"is-collapse-main":""},[a("div",{class:"fixed"===e.header?"fixed-header":""},[a("vab-nav-bar"),"true"===e.tabsBar||!0===e.tabsBar?a("vab-tabs-bar"):e._e()],1),a("vab-app-main")],1)],1),a("el-backtop")],1)},s=[],n=a("5530"),r=(a("caad"),a("2532"),a("2f62")),o=a("f121"),l={name:"Layout",data:function(){return{oldLayout:""}},computed:Object(n["a"])(Object(n["a"])({},Object(r["c"])({layout:"settings/layout",tabsBar:"settings/tabsBar",collapse:"settings/collapse",header:"settings/header",device:"settings/device"})),{},{classObj:function(){return{mobile:"mobile"===this.device}}}),beforeMount:function(){window.addEventListener("resize",this.handleResize)},beforeDestroy:function(){window.removeEventListener("resize",this.handleResize)},mounted:function(){var e=this;this.oldLayout=this.layout;var t=navigator.userAgent;t.includes("Juejin")&&this.$baseAlert("vue-admin-beautiful不支持在掘金内置浏览器演示，请手动复制以下地址到浏览器中查看http://mpfhrd48.sanxing.uz7.cn/vue-admin-beautiful");var a=this.handleIsMobile();a?(a?this.$store.dispatch("settings/changeLayout","vertical"):this.$store.dispatch("settings/changeLayout",this.oldLayout),this.$store.dispatch("settings/toggleDevice","mobile"),setTimeout((function(){e.$store.dispatch("settings/foldSideBar")}),2e3)):this.$store.dispatch("settings/openSideBar"),this.$nextTick((function(){window.addEventListener("storage",(function(e){e.key!==o["tokenName"]&&null!==e.key||window.location.reload(),e.key===o["tokenName"]&&null===e.value&&window.location.reload()}),!1)}))},methods:Object(n["a"])(Object(n["a"])({},Object(r["b"])({handleFoldSideBar:"settings/foldSideBar"})),{},{handleIsMobile:function(){return document.body.getBoundingClientRect().width-1<992},handleResize:function(){if(!document.hidden){var e=this.handleIsMobile();e?this.$store.dispatch("settings/changeLayout","vertical"):this.$store.dispatch("settings/changeLayout",this.oldLayout),this.$store.dispatch("settings/toggleDevice",e?"mobile":"desktop")}}})},c=l,u=(a("53bd"),a("2877")),d=Object(u["a"])(c,i,s,!1,null,"4e2d016a",null);t["default"]=d.exports},"4b32":function(e,t,a){"use strict";a("51b2")},"4d1b":function(e,t,a){e.exports={"menu-color":"rgba(255,255,255,.95)","menu-color-active":"rgba(255,255,255,.95)","menu-background":"#282c34"}},"51b2":function(e,t,a){},"53bd":function(e,t,a){"use strict";a("bc43")},"5f5f":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("router-view")},s=[],n=a("2877"),r={},o=Object(n["a"])(r,i,s,!1,null,null,null);t["default"]=o.exports},6997:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-breadcrumb",{staticClass:"breadcrumb-container",attrs:{separator:">"}},e._l(e.list,(function(t){return a("el-breadcrumb-item",{key:t.path},[e._v(" "+e._s(t.meta.title)+" ")])})),1)},s=[],n=(a("4de4"),a("d3b7"),a("b0c0"),{name:"VabBreadcrumb",data:function(){return{list:this.getBreadcrumb()}},watch:{$route:function(){this.list=this.getBreadcrumb()}},methods:{getBreadcrumb:function(){return this.$route.matched.filter((function(e){return e.name&&e.meta.title}))}}}),r=n,o=(a("8a85"),a("2877")),l=Object(o["a"])(r,i,s,!1,null,"2b4cacba",null);t["default"]=l.exports},"803c":function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return e.themeBar?a("span",[a("vab-icon",{attrs:{title:"主题配置",icon:["fas","palette"]},on:{click:e.handleOpenThemeBar}}),e.isOri?a("div",{staticClass:"theme-bar-setting"},[a("div",{on:{click:e.handleOpenThemeBar}},[a("vab-icon",{attrs:{icon:["fas","palette"]}}),a("p",[e._v("主题配置")])],1)]):e._e(),a("el-drawer",{attrs:{title:"主题配置",visible:e.drawerVisible,direction:"rtl","append-to-body":"",size:"470px"},on:{"update:visible":function(t){e.drawerVisible=t}}},[a("el-scrollbar",{staticStyle:{height:"94vh",overflow:"hidden"}},[a("div",{staticClass:"el-drawer__body"},[a("el-form",{ref:"form",attrs:{model:e.theme,"label-position":"top"}},[a("el-form-item",{attrs:{label:"主题"}},[a("el-radio-group",{model:{value:e.theme.name,callback:function(t){e.$set(e.theme,"name",t)},expression:"theme.name"}},[a("el-radio-button",{attrs:{label:"default"}},[e._v("默认")]),a("el-radio-button",{attrs:{label:"green"}},[e._v("绿荫草场")]),a("el-radio-button",{attrs:{label:"glory"}},[e._v("荣耀典藏")])],1)],1),a("el-form-item",{attrs:{label:"布局"}},[a("el-radio-group",{model:{value:e.theme.layout,callback:function(t){e.$set(e.theme,"layout",t)},expression:"theme.layout"}},[a("el-radio-button",{attrs:{label:"vertical"}},[e._v("纵向布局")]),a("el-radio-button",{attrs:{label:"horizontal"}},[e._v("横向布局")])],1)],1),a("el-form-item",{attrs:{label:"头部"}},[a("el-radio-group",{model:{value:e.theme.header,callback:function(t){e.$set(e.theme,"header",t)},expression:"theme.header"}},[a("el-radio-button",{attrs:{label:"fixed"}},[e._v("固定头部")]),a("el-radio-button",{attrs:{label:"noFixed"}},[e._v("不固定头部")])],1)],1),a("el-form-item",{attrs:{label:"多标签"}},[a("el-radio-group",{model:{value:e.theme.tabsBar,callback:function(t){e.$set(e.theme,"tabsBar",t)},expression:"theme.tabsBar"}},[a("el-radio-button",{attrs:{label:"true"}},[e._v("开启")]),a("el-radio-button",{attrs:{label:"false"}},[e._v("不开启")])],1)],1),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.handleSaveTheme}},[e._v(" 保存 ")])],1)],1)],1)])],1)],1):e._e()},s=[],n=a("5530"),r=(a("b0c0"),a("99af"),a("cf1e"),a("2f62")),o=a("f121"),l={name:"VabThemeBar",data:function(){return{isOri:o["isOri"],drawerVisible:!1,theme:{name:"default",layout:"",header:"fixed",tabsBar:""}}},computed:Object(n["a"])({},Object(r["c"])({layout:"settings/layout",header:"settings/header",tabsBar:"settings/tabsBar",themeBar:"settings/themeBar"})),created:function(){var e=this;this.$baseEventBus.$on("theme",(function(){e.handleOpenThemeBar()}));var t=localStorage.getItem("vue-admin-beautiful-theme");null!==t?(this.theme=JSON.parse(t),this.handleSetTheme()):(this.theme.layout=this.layout,this.theme.header=this.header,this.theme.tabsBar=this.tabsBar)},methods:Object(n["a"])(Object(n["a"])({},Object(r["b"])({changeLayout:"settings/changeLayout",changeHeader:"settings/changeHeader",changeTabsBar:"settings/changeTabsBar"})),{},{handleIsMobile:function(){return document.body.getBoundingClientRect().width-1<992},handleOpenThemeBar:function(){this.drawerVisible=!0},handleSetTheme:function(){var e=this.theme,t=e.name,a=e.layout,i=e.header,s=e.tabsBar;localStorage.setItem("vue-admin-beautiful-theme",'{\n          "name":"'.concat(t,'",\n          "layout":"').concat(a,'",\n          "header":"').concat(i,'",\n          "tabsBar":"').concat(s,'"\n        }')),this.handleIsMobile()||this.changeLayout(a),this.changeHeader(i),this.changeTabsBar(s),document.getElementsByTagName("body")[0].className="vue-admin-beautiful-theme-".concat(t),this.drawerVisible=!1},handleSaveTheme:function(){this.handleSetTheme()},handleSetDfaultTheme:function(){var e=this.theme.name;document.getElementsByTagName("body")[0].classList.remove("vue-admin-beautiful-theme-".concat(e)),localStorage.removeItem("vue-admin-beautiful-theme"),this.$refs["form"].resetFields(),Object.assign(this.$data,this.$options.data()),this.changeHeader(o["layout"]),this.theme.name="default",this.theme.layout=this.layout,this.theme.header=this.header,this.theme.tabsBar=this.tabsBar,this.drawerVisible=!1,location.reload()}})},c=l,u=(a("a86e"),a("ad67"),a("2877")),d=Object(u["a"])(c,i,s,!1,null,"3ab97b2b",null);t["default"]=d.exports},"84c8":function(e,t,a){"use strict";a.r(t);a("d3b7"),a("159b"),a("ddb0"),a("b0c0"),a("fb6a");var i=a("a026"),s=a("f192");s.keys().forEach((function(e){var t=s(e),a=t.default.name;i["default"].component(a,t.default||t)}));var n=a("e114");n.keys().forEach((function(e){var t=n(e),a=t.default.name;i["default"].component(a,t.default||t)}));var r=a("1654");r.keys().forEach((function(e){a("e8cc")("./".concat(e.slice(2)))}))},"89bf":function(e,t,a){},"8a85":function(e,t,a){"use strict";a("89bf")},"999c":function(e,t,a){},a86e:function(e,t,a){"use strict";a("20a2")},ab5f:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{class:"logo-container-"+e.layout},[a("router-link",{attrs:{to:"/"}},[e.logo?a("vab-remix-icon",{staticClass:"logo",attrs:{"icon-class":e.logo}}):e._e(),a("span",{staticClass:"title",class:{"hidden-xs-only":"horizontal"===e.layout},attrs:{title:e.title}},[e._v(" "+e._s(e.title)+" ")])],1)],1)},s=[],n=a("5530"),r=a("2f62"),o={name:"VabLogo",data:function(){return{title:this.$baseTitle}},computed:Object(n["a"])({},Object(r["c"])({logo:"settings/logo",layout:"settings/layout"}))},l=o,c=(a("4b32"),a("2877")),u=Object(c["a"])(l,i,s,!1,null,"713436e9",null);t["default"]=u.exports},ad67:function(e,t,a){"use strict";a("4d1b")},bc43:function(e,t,a){},cbe4:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("el-dropdown",{on:{command:e.handleCommand}},[a("span",{staticClass:"avatar-dropdown"},[a("img",{staticClass:"user-avatar",attrs:{src:e.avatar,alt:""}}),a("div",{staticClass:"user-name"},[e._v(" "+e._s(e.username)+" "),a("i",{staticClass:"el-icon-arrow-down el-icon--right"})])]),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[e.isOri?a("el-dropdown-item",{attrs:{command:"github"}},[e._v(" github地址 ")]):e._e(),e.isOri?a("el-dropdown-item",{attrs:{command:"gitee",divided:""}},[e._v(" 码云地址 ")]):e._e(),e.isOri?a("el-dropdown-item",{attrs:{command:"pro",divided:""}},[e._v(" pro付费版地址 ")]):e._e(),e.isOri?a("el-dropdown-item",{attrs:{command:"plus",divided:""}},[e._v(" plus付费版地址 ")]):e._e(),a("el-dropdown-item",{attrs:{command:"logout",divided:""}},[e._v("退出登录")])],1)],1)},s=[],n=a("1da1"),r=a("5530"),o=(a("96cf"),a("2f62")),l=a("f121"),c={name:"VabAvatar",data:function(){return{isOri:l["isOri"]}},computed:Object(r["a"])({},Object(o["c"])({avatar:"user/avatar",username:"user/username"})),methods:{handleCommand:function(e){switch(e){case"logout":this.logout();break;case"personalCenter":this.personalCenter();break;case"github":window.open("https://github.com/chuzhixin/vue-admin-beautiful");break;case"gitee":window.open("https://gitee.com/chu1204505056/vue-admin-beautiful");break;case"pro":window.open("https://chu1204505056.gitee.io/admin-pro/?hmsr=homeAd&hmpl=&hmcu=&hmkw=&hmci=");break;case"plus":window.open("https://chu1204505056.gitee.io/admin-plus/?hmsr=homeAd&hmpl=&hmcu=&hmkw=&hmci=")}},personalCenter:function(){this.$router.push("/personalCenter/personalCenter")},logout:function(){var e=this;this.$baseConfirm("您确定要退出"+this.$baseTitle+"吗?",null,Object(n["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$store.dispatch("user/logout");case 2:l["recordRoute"]?(a=e.$route.fullPath,e.$router.push("/login?redirect=".concat(a))):e.$router.push("/login");case 3:case"end":return t.stop()}}),t)}))))}}},u=c,d=(a("ed6e"),a("2877")),h=Object(d["a"])(u,i,s,!1,null,"5816a01d",null);t["default"]=h.exports},d137:function(e,t,a){"use strict";a("1294")},db38:function(e,t,a){"use strict";a("999c")},ed6e:function(e,t,a){"use strict";a("36cc")}}]);