# Environment Config

# store your secrets and config variables in here
# only invited collaborators will be able to see your .env values
# reference these in your code with process.env.SECRET

<#if project.nlpService == "DIALOGFLOW">
DIALOGFLOW_CLIENT_TOKEN=IM_A_FAKE_TOKEN_PLEASE_CHANGE_ME
DIALOGFLOW_DEVELOPER_TOKEN=
</#if>
<#if project.facebookBot>
ACCESS_TOKEN=
VERIFY_TOKEN=
</#if>
PORT=8090
DEBUG=*

# note: .env is a shell file so there can’t be spaces around =