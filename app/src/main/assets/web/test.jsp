<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html lang="zh-cn">
    <head>
<meta charset="utf-8">
       	<script src="three/js/Detector.js"></script>
	<script src="build/three.js"></script>
	<script src="three/js/controls/TrackballControls.js"></script
	>
	<script src="three/js/loaders/PLYLoader.js"></script>
	<script src="three/js/loaders/MTLLoader.js"></script>
	<script src="three/js/renderers/CanvasRenderer.js"></script>
	<script src="three/js/libs/dat.gui.min.js"></script>
	<script src="three/js/loaders/LoaderSupport.js"></script>
	<script src="three/js/controls/OrbitControls.js"></script>
	<script src="three/js/loaders/OBJLoader.js"></script>
	
	 <script type="text/javascript" src="three/js/libs/stats.min.js"></script>
<style type="text/css">
    #canvas3d{
margin:0;
/*border:1px solid red;*/
width:100%;      
height:600px;
    }
  
</style>
<style type="text/css">
      #progress{
margin:0; 
line-height:40px; 
font-weight:bold;
color:#fff; 
text-align:center;
background-color:#ff0000;
    }
</style>
 </head>
    <body>
    <div id="canvas3d">
		<div id = "progress">
     		<p></p>
		</div>       
    </div>
    <script type="text/javascript">
 var path = "three/models/obj/hairg/";
	if(Detector.webgl){
		init();
		 animate();
	 }else{
		 alert('浏览器不支持');
	 }
	var scene,camera,renderer;
	function init(){
	 scene = new THREE.Scene();
		 camera = new THREE.PerspectiveCamera(45, window.innerWidth/window.innerHeight, 0.1, 1000);
	  camera.position.set(0, 40, 50);
        camera.lookAt(new THREE.Vector3(0,0,0));
	var light = new THREE.PointLight(0xffffff);
        light.position.set(0,50,0);

        //告诉平行光需要开启阴影投射
        light.castShadow = true;

        scene.add(light);
	renderer = new THREE.WebGLRenderer();
	renderer.setSize(window.innerWidth, window.innerHeight);
	renderer.setClearColor(0xf0f0f0);
	//别忘记了这个要写滴...不然就真看不见画面。
	document.getElementById('canvas3d').appendChild(renderer.domElement);
	var manager = new THREE.LoadingManager();
	//模型需要纹理Texture
	var texture = new THREE.Texture();
	var loader = new THREE.ImageLoader( manager );
	loader.load(path+ 'SFa.png', function ( image ) { texture.image = image; texture.needsUpdate = true;
	} );
//obj加载的类如图：
//obj加载,构造函数的参数是LoadingManager
var loader = new THREE.OBJLoader(manager);
//加载方法有4个参数，分别是obj文件路径，加载完毕回调，加载进度回调，错误回调。
//我们先把几个回调写好
//加载完毕回调如下，加载完毕，我们做的就是把模型加载到场景中
var onLoad = function(object){
	object.traverse( function ( child ) {
			if ( child instanceof THREE.Mesh ) {
				child.material.map = texture;
			} 
		} );
		object.scale.set(50, 50, 100);
	scene.add( object);
}
//加载过程，可以显示进度值
var onProgress = function ( xhr ){};
//错误回调，因为网页调试有报错，这里可以不做处理
var onError = function ( xhr ) {
};
//回调写好了，现在可以用加载的方法加载模型了。
loader.load(path+'hair.obj',onLoad,onProgress,onError);}
window.onresize = function(){
	var w = document.getElementById("canvas3d").clientWidth;
	var h = document.getElementById("canvas3d").clientHeight;
	camera.aspect = w/h;
	camera.updateProjectionMatrix();
	renderer.setSize(w,h);
}
function render(){
	renderer.render(scene,camera);
}
function animate() {
	requestAnimationFrame( animate );
	render();
}</script>
    </body>
</html>

