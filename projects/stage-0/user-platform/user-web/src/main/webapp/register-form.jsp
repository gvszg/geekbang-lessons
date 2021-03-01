<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		<form class="form-horizontal" action='' method="POST">
          <fieldset>
            <div id="legend">
              <legend class="">Register</legend>
            </div>
            <div class="control-group">
              <!-- Username -->
              <label class="control-label"  for="username">Username</label>
              <div class="controls">
                <input type="text" id="username" name="username" placeholder="Username" class="input-xlarge">
                <p class="help-block">Username can contain any letters or numbers, without spaces</p>
              </div>
            </div>

            <div class="control-group">
              <!-- E-mail -->
              <label class="control-label" for="email">E-mail</label>
              <div class="controls">
                <input type="text" id="email" name="email" placeholder="Email" class="input-xlarge">
                <p class="help-block">Please provide your E-mail</p>
              </div>
            </div>

            <div class="control-group">
              <!-- Cell Phone -->
              <label class="control-label"  for="cell_phone">Cell Phone</label>
              <div class="controls">
                <input type="cell_phone" id="cell_phone" name="cell_phone" placeholder="Cell Phone" class="input-xlarge">
                <p class="help-block">Please provide your cell phone</p>
              </div>
            </div>

            <div class="control-group">
              <!-- Password-->
              <label class="control-label" for="password">Password</label>
              <div class="controls">
                <input type="password" id="password" name="password" placeholder="Password" class="input-xlarge">
                <p class="help-block">Password should be at least 4 characters</p>
              </div>
            </div>

            <div class="control-group">
              <!-- Button -->
              <div class="controls">
                <button class="btn btn-success">Register</button>
              </div>
            </div>
          </fieldset>
        </form>
	</div>
</body>