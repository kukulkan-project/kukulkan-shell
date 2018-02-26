# Environment Config

# store your secrets and config variables in here
# only invited collaborators will be able to see your .env values
# reference these in your code with process.env.SECRET

<#if project.nlpService == "DIALOGFLOW">
DIALOGFLOW_CLIENT_TOKEN=${project.dialogflowConfig.clientToken}
DIALOGFLOW_DEVELOPER_TOKEN=${project.dialogflowConfig.developerToken}
</#if>
<#if project.facebookBot>
ACCESS_TOKEN=${project.facebookBotConfig.accessToken}
VERIFY_TOKEN=${project.facebookBotConfig.verifyToken}
</#if>
PORT=${project.port?string.computer}
<#if project.webhook>
FULFILLMENT_ENDPOINT=${project.webhookConfig.endpoint}
</#if>
<#if project.webBot>
WEB_BOT_PORT=3000
</#if>
TOKEN_FULFILLMENT=

# note: .env is a shell file so there can’t be spaces around =