<head>
<jsp:directive.include
	file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
<title>My Home Page</title>
</head>
<body>
	<div class="container-lg">
		<!-- Content here -->
		<form class="form-horizontal" action="/register" method="POST">
          <fieldset>
            <div id="legend">
              <legend class="">Register</legend>
            </div>
            <div class="control-group">
              <!-- Name -->
              <label class="control-label"  for="name">Name</label>
              <div class="controls">
                <input type="text" id="name" name="name" placeholder="name" class="input-xlarge">
              </div>
            </div>

            <div class="control-group">
              <!-- Email -->
              <label class="control-label" for="email">E-mail</label>
              <div class="controls">
                <input type="text" id="email" name="email" placeholder="Email" class="input-xlarge">
                <p class="help-block">Please provide your E-mail</p>
              </div>
            </div>

            <div class="control-group">
              <!-- phoneNumber -->
              <label class="control-label"  for="phoneNumber">Phone Number</label>
              <div class="controls">
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone Number" class="input-xlarge">
                <p class="help-block">Please provide your Phone Number</p>
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