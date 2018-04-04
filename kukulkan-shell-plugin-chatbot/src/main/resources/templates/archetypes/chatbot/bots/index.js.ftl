// Index for creating bots
var botkit = require('botkit');
<#if project.webBot>
var webController = require('./web/controller');
var webMiddlewares = require('./web/middlewares');
var webSkills = require('./web/skills');
</#if>

<#if project.facebookBot>
var fbController = require('./facebook/controller');
var fbMiddlewares = require('./facebook/middlewares');
var fbSkills = require('./facebook/skills');
var fbMenu = require('./facebook/menu');
</#if>

<#if project.webBot>
//Creating web bot
function createWebBot(botkit, server) {
    var controller = webController(botkit, server);
    webMiddlewares(controller);
    webSkills(controller);
}
</#if>

<#if project.facebookBot>
//Creating Facebook bot
function createFbBot(botkit, app) {
    var controller = fbController(botkit, app);
    fbMiddlewares(controller);
    fbSkills(controller);
    fbMenu(controller);
}
</#if>

module.exports = function (server, app) {
    <#if project.webBot>
    createWebBot(botkit, server);
    </#if>
    <#if project.facebookBot>
    createFbBot(botkit, app);
    </#if>
}