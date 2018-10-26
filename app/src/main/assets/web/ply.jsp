<!DOCTYPE html>
<html lang="en">
<head>
<title>three.js webgl - OBJLoader2 basic usage</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
 <style>        body {          
   /* set margin to 0 and overflow to hidden, to go fullscreen */          
     margin: 0;            overflow: hidden;        }   
      </style>
      </head>
     <body onload="draw();">
      <div id="Stats-output"></div>
      <!-- Div which will hold the Output -->
      <div id="WebGL-output"></div>

<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
	<script src="three/js/Detector.js"></script>
	<script src="build/three.js"></script>
	<script src="three/js/controls/TrackballControls.js"></script
	>
	<script src="three/js/loaders/PLYLoader.js"></script>
	<script src="three/js/loaders/FBXLoader.js"></script>
	<script src="three/js/loaders/MTLLoader.js"></script>
	<script src="three/js/renderers/CanvasRenderer.js"></script>
	<script src="three/js/libs/dat.gui.min.js"></script>
	<script src="three/js/libs/inflate.min.js"></script>
	<script src="three/js/loaders/LoaderSupport.js"></script>
	<script src="three/js/controls/OrbitControls.js"></script>
	<script src="three/js/loaders/OBJLoader.js"></script>
	<script type="text/javascript" src="three/crypto-js.js"></script>
	<script type="text/javascript" src="three/aes.js"></script>
	 <script type="text/javascript" src="three/js/libs/stats.min.js"></script>
<script>
    var renderer;
    var path = "three/models/obj/hairg/";
    var pathply = "three/models/fbx/1/";
    
    // aes解密
	function decrypt(word) {
	    var key = CryptoJS.enc.Utf8.parse('1234123412341324');
	    var iv  = CryptoJS.enc.Utf8.parse('1234123412341234');
	
	    var decrypt = CryptoJS.AES.decrypt(word, key, {
	        iv: iv,
	        mode: CryptoJS.mode.CBC,
	        padding: CryptoJS.pad.Pkcs7
	    });
	    var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
	    return decryptedStr.toString();
	}
   
    function initRender() {
        renderer = new THREE.WebGLRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        //告诉渲染器需要阴影效果
        renderer.setClearColor(0xffffff);
        
		renderer.shadowMapEnabled = true;
        document.body.appendChild(renderer.domElement);
    }
    
    function GetRequest() { 
	   var url = location.search; //获取url中"?"符后的字串 
	   var theRequest = new Object(); 
	   if (url.indexOf("?") != -1) { 
	      var str = url.substr(1); 
	      strs = str.split("&"); 
	      for(var i = 0; i < strs.length; i ++) { 
	         theRequest[strs[i].split("-")[0]]=unescape(strs[i].split("-")[1]); 
	      } 
	   } 
	   pathply = theRequest['p'];
	   alert(decrypt(pathply)+"|||"+pathply);
	   return theRequest; 
	
	}

    var camera;
    function initCamera() {
        camera = new THREE.PerspectiveCamera(45, window.innerWidth/window.innerHeight, 0.1, 1000);
        camera.position.set(0, 40, 50);
        camera.lookAt(new THREE.Vector3(0,0,0));
    }

    var scene;
    function initScene() {
        scene = new THREE.Scene();
    }

    //初始化dat.GUI简化试验流程
    var gui;
    function initGui() {
        //声明一个保存需求修改的相关数据的对象
        gui = {
        };
        var datGui = new dat.GUI();
        //将设置属性添加到gui当中，gui.add(对象，属性，最小值，最大值）
    }

    var light;
    function initLight() {
        scene.add(new THREE.AmbientLight(0xddffff));

       /*  light = new THREE.PointLight(0xdddfff);
        light.position.set(0,120,0);

        //告诉平行光需要开启阴影投射
        light.castShadow = true; */

        scene.add(light);
    }

    function initModel() {

        //辅助工具
        //var helper = new THREE.AxesHelper(50);
        //scene.add(helper);

        // model
        var fbxloader = new THREE.FBXLoader();
        fbxloader.load(pathply+'model.fbx', function ( object ) {
    		object.scale.multiplyScalar(120);    // 缩放模型大小
            object.traverse(function ( child ) {
  			
                if ( child.isMesh ) {
 
                    child.castShadow = true;
                    child.receiveShadow = true;
 
                }
 
            } );
 
            scene.add( object );
 
        } );
	
   var manager = new THREE.LoadingManager();
	var texture = new THREE.Texture();
	var mtlLoader = new THREE.MTLLoader();
	mtlLoader.setPath(path);
	  mtlLoader.load('xmhair.mtl', function (material) {
	  	
	  	var loader = new THREE.ImageLoader( manager );
	  	var caizhi = material.materialsInfo.lambert7SG;
		loader.load(path+ caizhi.map_kd, function ( image ) { texture.image = image; texture.needsUpdate = true;
	} );
   		
            var objLoader = new THREE.OBJLoader();
            //设置当前加载的纹理
            //objLoader.setMaterials(material);
            objLoader.setPath(path);
            objLoader.load('xmhair.obj', function (object) {
            
      
				 object.traverse(function(child) { 
            		if (child instanceof THREE.Mesh) { 
		                child.material.map = texture;//设置贴图模式为双面贴图
		            	child.material.side = THREE.DoubleSide;
		                child.material.transparent=true;//材质允许透明
		                child.material.overdraw=0.8;
		                child.material.opacity=1;//材质默认透明度          
		                child.material.depthTest = true;              
		                child.material.alphaTest=0.75;//平滑渲染
		             
           		 }
        	});
        	object.emissive=0x00ffff;//自发光颜色
        	object.ambient=0x00ffff;//环境光颜色                //获取两个翅膀的位置
               // var wing2 = object.children[0];
             

               /*  //设置两个翅膀的透明度
                wing1.material.opacity = 0.6;
                wing1.material.transparent = true;
                wing1.material.depthTest = false;
                wing1.material.side = THREE.DoubleSide;
 */
                //wing2.material.opacity = 1;
               // wing2.material.depthTest = false;
               // wing2.material.transparent = true;
               // wing2.material.side = THREE.DoubleSide;
				//wing2.material.shading = THREE.FlatShading;
                //将模型缩放并添加到场景当中
                object.scale.set(120, 120, 120);
                scene.add(object);
        
        });
	  	
	  });
	
    }
 
    //初始化性能插件
    var stats;
    function initStats() {
        stats = new Stats();
        document.body.appendChild(stats.dom);
    }

    //用户交互插件 鼠标左键按住旋转，右键按住平移，滚轮缩放
    var controls;
    function initControls() {

        controls = new THREE.OrbitControls( camera, renderer.domElement );
		controls.target = new THREE.Vector3(0, 0, 0);//控制焦点
        // 如果使用animate方法时，将此函数删除
        //controls.addEventListener( 'change', render );
        // 使动画循环使用时阻尼或自转 意思是否有惯性
        controls.enableDamping = true;
        //动态阻尼系数 就是鼠标拖拽旋转灵敏度
        controls.dampingFactor = 0.25;
        //是否可以缩放
        controls.enableZoom = false;
        //是否自动旋转
        controls.autoRotate = true;
        //设置相机距离原点的最远距离
        controls.minDistance  = 1;
        //设置相机距离原点的最远距离
        controls.maxDistance  = 200;
        //是否开启右键拖拽
        controls.enablePan = true;
    }

    function render() {

        renderer.render( scene, camera );
    }

    //窗口变动触发的函数
    function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        render();
        renderer.setSize( window.innerWidth, window.innerHeight );

    }

    function animate() {
        //更新控制器
        render();

        //更新性能插件
       // stats.update();

       // controls.update();

        requestAnimationFrame(animate);
    }

    function draw() {
    	GetRequest();
        initGui();
        initRender();
        initScene();
        initCamera();
        initLight();
        initModel();
        initControls();
        initStats();

        animate();
        window.onresize = onWindowResize;
    }
</script>


</body>
</html>
