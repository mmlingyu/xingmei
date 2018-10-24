
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <title>H5-3D模型展示</title>

		<link href="index.css" rel="stylesheet" />
        
        <!-- Babylon.js -->
  
        <script src="three/js/babylon.js"></script>
       <!--  <script src="https://preview.babylonjs.com/babylon.js"></script>> -->
        <script src="three/js/babylonjs.loaders.min.js"></script>
        <script src="three/js/babylon.gui.min.js"></script>
        <script src="three/js/pep.min.js"></script>

        <style>
            html, body {
                overflow: hidden;
                width: 100%;
                height: 100%;
                margin: 0;
                padding: 0;
            }

            #renderCanvas {
                width: 100%;
                height: 100%;
                touch-action: none;
            }
        </style>
    </head>
<body>

    <div id="sponzaLoader">
            <div id="backgroundImage"></div>
            <div id="loadingDetails">
                <div id="loadingDetailsBackground"></div>
                <div id="loadingPercentage">
                    <div id="loadingTitle">H5展示</div>
                    <div id="teamText">H5</div>
                    <div id="textPercentage">0%</div>
                    <div id="progressContainer">
                        <progress id="loadingProgress" value="0" max="100"></progress>
                    </div>
                    <div id="streamingText">下载中</div>
            </div>
        </div>
    </div>
	<div class="texts">H5</div>
    <canvas id="renderCanvas" touch-action="none"></canvas>
    <script  src="three/js/main.js"></script>
<script type="text/javascript"  src="https://idm-su.baidu.com/su.js"></script>
</body>
</html>
