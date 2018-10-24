//----------------------------------------------------
var textPercentage = document.getElementById("textPercentage");
var loadingProgress = document.getElementById("loadingProgress");
var backgroundImage = document.getElementById("backgroundImage");
var progressContainer = document.getElementById("progressContainer");
var streamingText = document.getElementById("streamingText");


function updateProgressDisplay(percentageValue) {
	backgroundImage.style.opacity = (percentageValue / 100).toString();
	textPercentage.innerHTML = percentageValue.toFixed() + "%";
	loadingProgress.value = percentageValue;
}

//----------------------------------------------------

var canvas = document.getElementById("renderCanvas");

var delayCreateScene = function () {

	var u = navigator.userAgent;
	  
	engine.enableOfflineSupport = false;
	
	
	// This is really important to tell Babylon.js to use decomposeLerp and matrix interpolation
	BABYLON.Animation.AllowMatricesInterpolation = true;

	var scene = new BABYLON.Scene(engine);
	
//鐜棰滆壊闇�璁剧疆鍦烘櫙鐨勭幆澧冮鑹诧紝鎻愪緵鐜鑳屾櫙鐓ф槑銆�	//
	scene.ambientColor = new BABYLON.Color3(1, 1, 1);
	
//璁剧疆鍔犺浇杩涘害-寮�
	var ITEMSTOSTREAM = 5;
	var progress = 0;
	function updateProgressWithRemainingItems() {
		var remaining = scene.getWaitingItemsCount();

		if (remaining > 0) {
			progress =  10 + progress + remaining + (remaining / ITEMSTOSTREAM);
			if(progress < 100){
				updateProgressDisplay(progress);
				//console.log("remaining=" + progress);
			}else{
				progress = 100;
				updateProgressDisplay(100);
				//console.log("remaining=" + progress);
			}
			requestAnimationFrame(updateProgressWithRemainingItems);
		}
		else {
			progress = 100;
			progressContainer.style.display = "none";
			streamingText.innerHTML = "涓嬭浇瀹屾垚";
			sponzaLoader.style.display = "none";
		}
	}
	//streamingText.innerHTML = "streaming items";
	requestAnimationFrame(updateProgressWithRemainingItems);
	// console.log(scene.cameras.length)
	// scene.activeCamera = scene.cameras[0];                
//璁剧疆鍔犺浇杩涘害-缁撴潫

//---------骞冲彴澶勭悊--------------
	var u = navigator.userAgent, 
	app = navigator.appVersion;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1;
	var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);

	// if (navigator.platform == "Windows" || navigator.platform == "Win32") {
	// 	//杩欎釜鏄畨鍗撴搷浣滅郴缁�	// 	console.log("pc鎿嶄綔绯荤粺");
	//  }
    if (isAndroid) {
	   //杩欎釜鏄畨鍗撴搷浣滅郴缁�	   console.log("瀹夊崜鎿嶄綔绯荤粺");
		var oldUp = scene._onPointerUp,
		oldDown = scene._onPointerDown,
		oldMove = scene._onPointerMove;

		var eventPrefix = BABYLON.Tools.GetPointerPrefix();
		canvas.removeEventListener(eventPrefix + "move", oldMove);
		canvas.removeEventListener(eventPrefix + "down", oldDown);
		window.removeEventListener(eventPrefix + "up", oldUp);
		
		// Wheel
		scene._onPointerUp = function(evt){
			//console.log(1);
			if(false === evt.pointerId > 0){ return;}
			oldUp(evt);
		}

		scene._onPointerDown = function(evt){
			//console.log(2);
			if(false === evt.pointerId > 0){ return;}
			oldDown(evt);
		}

		scene._onPointerMove = function(evt){
			if(false === evt.pointerId > 0){ return;}
			oldMove(evt);
		}

		canvas.addEventListener(eventPrefix + "move", scene._onPointerMove, false);
		// Whee
		canvas.addEventListener(eventPrefix + "down", scene._onPointerDown, false);
		window.addEventListener(eventPrefix + "up", scene._onPointerUp, false);

    }
//     if (isIOS) {
// 銆��銆��//杩欎釜鏄痠os鎿嶄綔绯荤粺
// 		console.log("ios鎿嶄綔绯荤粺");
//     }
//-----------------------

	//缁曟寚瀹氱殑鐩爣杩涜鏃嬭浆鐨勭涓変汉绉拌瀵熷瀷鎽勫儚鏈�	
    var camera = new BABYLON.ArcRotateCamera("camera1", Math.PI / 2, Math.PI / 4, 3, new BABYLON.Vector3(0, 0, 0), scene);
	////璁ヽanvas浣跨敤鐩告満
	camera.attachControl(canvas, true);

	//闄愬埗鐩告満鏀惧ぇ缂╁皬 鏈�ぇ鍙婃渶灏忚窛绂�	
	camera.lowerRadiusLimit = 2;
	camera.upperRadiusLimit = 10;
	//camera.wheelDeltaPercentage = 0.01;

	camera.wheelPrecision = 50;
	camera.pinchPrecision = 500;
	
	//闄愬埗鐩告満绌胯繘鐗╀綋
	camera.minZ = 1.0;
	//闄愬埗鍒濆鏃嬭浆瑙掑害 鏃嬭浆鍜岀墿浣撳钩琛屽垯鍋滄
	camera.upperBetaLimit = Math.PI / 2;


	var light = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(0, 1, 0), scene);
	//鐏厜寮哄害
	light.intensity = 2.6;
	// light.specular = BABYLON.Color3.Black();

	var light2 = new BABYLON.DirectionalLight("dir01", new BABYLON.Vector3(0, -0.5, -1.0), scene);
	light2.position = new BABYLON.Vector3(0, 2, 0);
	light2.intensity = 2;

	// Shadows
	var shadowGenerator = new BABYLON.ShadowGenerator(1024, light2);
	shadowGenerator.useBlurExponentialShadowMap = true;
	shadowGenerator.blurKernel = 32;


	var hdrTexture = new BABYLON.CubeTexture.CreateFromPrefilteredData("three/assets/environment.dds", scene);
	hdrTexture.gammaSpace = false;
	
	var sky = scene.createDefaultSkybox(hdrTexture, true, 100, 0.5);
	for (var index = 0; index < scene.meshes.length; index++) {
		var m = scene.meshes[index];
		if (m === sky) {
			continue;
		}
		m.receiveShadows = true;
		//generator.getShadowMap().renderList.push(m);
	}

		
	var loader = new BABYLON.AssetsManager(scene);
	
	var house = loader.addMeshTask("assets", "", "three/assets/", "hair.babylon");
	house.onSuccess = function (task) {
		console.log('---> DONE', task)
		task.loadedMeshes.forEach(function (m) {
			//m.material.diffuseColor = new BABYLON.Color3(0, 0, 0);
			m.material.specularColor = new BABYLON.Color3(0, 0, 0);
			//m.scaling = new BABYLON.Vector3(5, 5, 5);
			m.position = BABYLON.Vector3.Zero();
			shadowGenerator.getShadowMap().renderList.push(m);
			m.showBoundingBox = true;

			// var myMaterial = new BABYLON.StandardMaterial("myMaterial", scene);
			//婕弽灏勮创鍥�			// myMaterial.diffuseColor = new BABYLON.Color3(1, 0, 1);
			//楂樺厜鍙嶅皠璐村浘
			// myMaterial.specularColor = new BABYLON.Color3(0.5, 0.6, 0.87);
			//鑷彂鍏夎壊
			// myMaterial.emissiveColor = new BABYLON.Color3(1, 1, 1);
			//鐜鍏夐鑹�			// myMaterial.ambientColor = new BABYLON.Color3(0.23, 0.98, 0.53);		 
			// mesh.material = myMaterial;
			//閫忔槑搴﹂鑹茬殑渚嬪瓙
			//var myMaterial.alpha = 0.5;
			//myMaterial.diffuseTexture.hasAlpha = true;
		});
	}
	
	house.onError = function() {
	console.log('ERROR', arguments);
	}
	loader.load();
	

return scene;
};


var engine = new BABYLON.Engine(canvas, true, { preserveDrawingBuffer: true, stencil: true });

var scene = delayCreateScene();

engine.runRenderLoop(function () {
	if (scene) {
		scene.render();
	}
});

// Resize
window.addEventListener("resize", function () {
	engine.resize();
});


function GetRequest() {
	var url = location.search; //鑾峰彇url涓�?"绗﹀悗鐨勫瓧涓�	
	var theRequest = new Object();
	if (url.indexOf("?") != -1) {
		var str = url.substr(1);
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) {
			theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		}
	}
	return theRequest;
}