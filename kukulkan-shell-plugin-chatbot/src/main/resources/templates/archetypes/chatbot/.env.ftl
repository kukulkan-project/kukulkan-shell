# Environment Config

# store your secrets and config variables in here
# only invited collaborators will be able to see your .env values
# reference these in your code with process.env.SECRET

<#if project.nlpService == "DIALOGFLOW">
DIALOGFLOW_CLIENT_TOKEN=
DIALOGFLOW_DEVELOPER_TOKEN=
</#if>
<#if project.facebookBot>
ACCESS_TOKEN=
VERIFY_TOKEN=
</#if>
PORT=${project.port?string.computer}
FULFILLMENT_ENDPOINT=${project.webhookConfig.endpoint}
WEB_BOT_PORT=3000

# note: .env is a shell file so there can’t be spaces around =