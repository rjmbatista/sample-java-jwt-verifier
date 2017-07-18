<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/jumbotron-narrow.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.growl.css"/>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="/js/jquery.growl.js" type="text/javascript"></script>
</head>

<body>

<div class="container col-lg-8">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-left">
                <li class="active" id="home"><a href="#">Home</a></li>
                <li id="verify_header"><a href="#">Verify</a></li>
                <li id="logout"><a href="#">Logout</a></li>
            </ul>
        </nav>
    </div>

    <div>
        accessToken: ${accessToken} <button id="verify">Verify</button><br><br>
        idToken: ${idToken}<br>
    </div>

</div>

<script type="text/javascript">
    $("#logout").click(function(e) {
        e.preventDefault();
        $("#home").removeClass("active");
        $("#password-login").removeClass("active");
        $("#logout").addClass("active");
        // assumes we are not part of SSO so just logout of local session
        window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/logout";
    });

    $("#verify_header").click(function(e) {
        e.preventDefault();
        window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/verify";
    });

    $("#verify").click(function(e) {
        e.preventDefault();
        window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/verify";
    });
</script>

</body>
</html>