import{j as _,u as d,o as w,c as i,w as t,a as s,b as c,y as f,z as V,A as b,B as g}from"./index-f4Nd3ITL.js";import{r as x}from"./api-BU9eJDJQ.js";const k=_({__name:"Register",setup(y){const e=d({username:"",password:"",password2:""}),m=()=>{if(e.password!=e.password2){alert("两次密码不一致");return}x(e.username,e.password).then(l=>{const o=d({code:l.data.base.code,message:l.data.base.message});alert(o.message)})};return(l,o)=>{const r=f,n=V,p=b,u=g;return w(),i(u,{"label-position":"top",style:{width:"360px"}},{default:t(()=>[s(n,{label:"用户名"},{default:t(()=>[s(r,{modelValue:e.username,"onUpdate:modelValue":o[0]||(o[0]=a=>e.username=a)},null,8,["modelValue"])]),_:1}),s(n,{label:"密码"},{default:t(()=>[s(r,{modelValue:e.password,"onUpdate:modelValue":o[1]||(o[1]=a=>e.password=a),type:"password","show-password":!0},null,8,["modelValue"])]),_:1}),s(n,{label:"确认密码"},{default:t(()=>[s(r,{modelValue:e.password2,"onUpdate:modelValue":o[2]||(o[2]=a=>e.password2=a),type:"password","show-password":!0},null,8,["modelValue"])]),_:1}),s(p,{onClick:m},{default:t(()=>[c("登录")]),_:1})]),_:1})}}});export{k as default};
