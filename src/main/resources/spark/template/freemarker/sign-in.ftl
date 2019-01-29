<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
      <a href="/">my home</a>
    </div>

    <div class="body">

      <p>Please write a player username.</p>

      <form action="/sign-in" method="POST">
        <label for="username">Name: </label>
        <input type="text" name="username" required />
        <br/><br/>
        <input type="submit" value="Enter" />
      </form>

      <#if message??>
        <div>${message}</div>
      </#if>
    </div>
  </div>
</body>
</html>