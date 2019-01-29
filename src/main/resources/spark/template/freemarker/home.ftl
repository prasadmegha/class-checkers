<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <meta http-equiv="refresh" content="7">
</head>
<body>
<div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
    <#if isLoggedIn>
      <a href="/">my home</a> |
      <a href="/sign-out">sign out [${playerName}]</a>
    <#else>
      <a href="/sign-in">sign in</a>
    </#if>
    </div>

    <div class="body">
        <p>Welcome to the world of online Checkers.</p>

    <#if message??>
      <div>${message}</div>
    </#if>

        <p><b>Players Online</b></p>

        <table>
          <form action="/game" method="GET">
            <#list playerList as player>
                <#if player != playerName>
                  <tr>
                    <input type="radio" name="opponentRadio" value="${player}" required/> ${player}
                    <br>
                  </tr>
                </#if>
            <#else>
                No players online yet.
                <br>
            </#list>

            <br>

            <#if isLoggedIn && playerList?size gt 1>
              <input type="submit" value="Let's play!" />
            <#else>
              <input type="submit" value="Let's play!" disabled />
            </#if>
          </form>
        </table>
    </div>
</div>
</body>
</html>