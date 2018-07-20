<div class="box">

    <h1>Embed</h1>
    <p>
        Copy the code below to embed this bot in a different website.

        <a href="https://github.com/howdyai/botkit-starter-web/blob/master/docs/botkit_web_client.md#embed-botkit-in-a-website-with-iframes">Read the guide to using embedded Botkit.</a>
    </p>

    <div class="addon">
        <textarea id="copy_pasta_1" rows="6" class="copyurl">
<div id="embedded_messenger">
    <header id="message_header" onclick="Botkit.toggle()">Chat</header>
    <iframe  id="botkit_client" src="//{{base_url}}/chat.html"></iframe>
</div>
<script src="//{{base_url}}/javascripts/embed.js"></script>
<script>
    var options = {}; // for options, see: https://github.com/howdyai/botkit-starter-web/blob/master/docs/botkit_web_client.md#botkitbootuser
    Botkit.boot(options);
</script>
<link rel="stylesheet" href="//{{base_url}}/css/embed.css" />
    </textarea>
        <button class="textarea" onclick="clipboard('copy_pasta_1')">Copy</button>
    </div>

</div>



<div id="embedded_messenger">
    <header id="message_header" onclick="Botkit.toggle()">Chat</header>
    <iframe id="botkit_client" src="/chat.html"></iframe>
</div>
<script src="/javascripts/embed.js"></script>
<link rel="stylesheet" href="/css/embed.css" />
<script>
    Botkit.boot();
</script>