<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Brainyx</title>
    
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
	<script language="javascript" type="text/javascript">
	    function changeImage(img){
	       document.getElementById('bigImage').src=img;
	    }
	    
	    
	    
		//롤오버
		function MM_preloadImages() { //v3.0
			var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
			var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
			if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
		}
		function MM_swapImgRestore() { //v3.0
			var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
		}
		function MM_findObj(n, d) { //v4.01
			var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
			d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
			if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
			for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
			if(!x && d.getElementById) x=d.getElementById(n); return x;
		}

		function MM_swapImage() { //v3.0
			var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
			if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
		}
		
		
		
	</script>
	



</script>

    <!-- favicon -->
    <link rel="icon" type="image/png" href="assets/images/other_images/favicon.png">

    <!-- Bootstrap core CSS -->
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="assets/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- owl carousel css -->
    <link href="assets/js/owl-carousel/owl.carousel.css" rel="stylesheet">
    <link href="assets/js/owl-carousel/owl.theme.css" rel="stylesheet">
    <link href="assets/js/owl-carousel/owl.transitions.css" rel="stylesheet">
    <!-- intro animations -->
    <link href="assets/js/wow/animate.css" rel="stylesheet">
    <!-- font awesome -->
    <link href="assets/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- lightbox -->
    <link href="assets/js/lightbox/css/lightbox.css" rel="stylesheet">

    <!-- styles for this template -->
    <link href="assets/css/styles.css" rel="stylesheet">

    <!-- place your extra custom styles in this file -->
    <link href="assets/css/custom.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <div class="background-image-overlay"></div>

    <div id="outer-background-container" data-default-background-img="assets/images/other_images/bg5.jpg" style="background-image:url(assets/images/other_images/bg5.jpg);"></div>
    <!-- end: #outer-background-container -->    

    <!-- Outer Container -->
    <div id="outer-container">

      <!-- Left Sidebar -->
      <section id="left-sidebar">
        
        <div class="logo">
          <a href="#intro" class="link-scroll"><img src="assets/images/brainyx_images/logo.png" alt="Brainyx"></a>
        </div><!-- .logo -->

        <!-- Menu Icon for smaller viewports -->
        <div id="mobile-menu-icon" class="visible-xs" onClick="toggle_main_menu();"><span class="glyphicon glyphicon-th"></span></div>

        <ul id="main-menu">
          <li id="menu-item-menu1" class="menu-item scroll"><a href="#menu1">앱개발 가이드</a></li>
          <li id="menu-item-menu2" class="menu-item scroll"><a href="#menu2">비용절감 프로젝트</a></li>
          <li id="menu-item-menu3" class="menu-item scroll"><a href="#menu3">사업분야</a></li>
          <li id="menu-item-menu3_1" class="menu-item scroll"><a href="#menu3_1">보유기술</a></li>
          <li id="menu-item-menu4" class="menu-item scroll"><a href="#menu4">공장특징</a></li>
          <li id="menu-item-menu5" class="menu-item scroll"><a href="#menu5">운영관리</a></li>
          <li id="menu-item-menu6" class="menu-item scroll"><a href="#menu6">포트폴리오</a></li>
          <li id="menu-item-menu7" class="menu-item scroll"><a href="#menu7">기술력</a></li>
          <li id="menu-item-menu8" class="menu-item scroll"><a href="#menu8">회사소개</a></li>
          <li id="menu-item-menu9" class="menu-item scroll"><a href="#menu9">1472시리즈</a></li>
        </ul><!-- #main-menu -->

      </section><!-- #left-sidebar -->
      <!-- end: Left Sidebar -->

      <section id="main-content" class="clearfix">
        
        
        <article id="intro" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg_main.jpg">
          <div class="content-wrapper clearfix" >

              <div id="features-carousel" class="carousel slide with-title-indicators max-height" data-height-percent="60" data-ride="carousel" style="MARGIN: 160px 0px 0px 0px">
                
                <!-- Indicators - slide navigation -->
                <ol class="carousel-indicators title-indicators">
                  <li data-target="#features-carousel" data-slide-to="0" class="active"><img src="assets/images/brainyx_images/m_tetragon_ico.gif"/> </li>
                  <li data-target="#features-carousel" data-slide-to="1"><img src="assets/images/brainyx_images/m_tetragon_ico.gif"/></li>
                  <li data-target="#features-carousel" data-slide-to="2"><img src="assets/images/brainyx_images/m_tetragon_ico.gif"/></li>
                  <li data-target="#features-carousel" data-slide-to="3"><img src="assets/images/brainyx_images/m_tetragon_ico.gif"/></li>
                </ol>

                <!-- Wrapper for slides -->
                <div class="carousel-inner">

                  <div class="item active">
                    <div class="carousel-text-content" >
                      <h6 class="title">다 소프트웨어 입니다.</h6>
                      <img src="assets/images/brainyx_images/m_title_line.gif" align="center" style="MARGIN: 10px 0px 2px 0px">
                      <p class="feature-paragraph"></p>
                      <div class = "intro-text" >
                      <p >어플! 앱! 어플리케이션! 모두 다 같은 말입니다.<br/>
							응용프로그램  그러니까 application software 입니다.</p>
							
							<p >스마트폰 시대가 되면서 소프트웨어 보다 가볍게 보이게<br/>
							스티브잡스 아저씨가 머리를 약간 썼지만<br/>
							소프트웨어 개발 회사 입장에서 볼 때는 예전 보다<br />
							일은 많아지고 가격은 싸지고 기술력은 더 필요하지요.</p>
							
							<p >스마트폰의 작은 화면에서 작동 된다고 쉽게 싸게<br/>
							또는 아무나에게 맡기시면 화 나는 일만 생길지도 모릅니다.</p>
							
							<p >보이는 작은 화면 보다 보이지 않는 서버구성과 기술력<br/>
							그리고 무엇보다 당신의 꿈을 만들어 드릴 수 있는 <br/>
							친절함을 가진 브레닉스에서 …
							</p>
							</div>
							<a href="#menu7" class="link-scroll"><img src="assets/images/brainyx_images/m_intro_btn.gif" 
							onmouseover="this.src='assets/images/brainyx_images/m_intro_btn_dw.gif';"
							onmouseout="this.src='assets/images/brainyx_images/m_intro_btn.gif';"></a>
							
                      <div class="content-to-populate-in-modal" id="modal-content-2">
                        <h1>Lorem Ipsum</h1>
                        <p><img data-img-src="assets/images/other_images/transp-image1.png" class="lazy rounded_border hover_effect pull-left" alt="Lorem Ipsum">Etiam at ligula sit amet arcu laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. <a href="#">Suspendisse molestie lorem odio</a>, sit amet. </p>
                        <p>Laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio, sit amet.</p>
                        <p>Suspendisse molestie lorem odio, sit amet. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio test.</p>
                      </div><!-- #modal-content-2 -->
                    </div>
                  </div><!-- .item -->

                  <div class="item">
                    <div class="carousel-text-content">
                      <h6 class="title">소프트웨어(앱)는 생명체 입니다.</h6>
                      <img src="assets/images/brainyx_images/m_title_line.gif" align="center" style="MARGIN: 10px 0px 2px 0px">
                      <p class="feature-paragraph"></p>
                      <div class = "intro-text" >
                      <p >앱!<br/>
							당신의 미래를 열어 줄 열쇠 입니다.<br/>
							당신이 정성을 쏟고 가꾸어야 할 미래 입니다.</p>
							
							<p >그것을 만들고 싶어 하는 것이잖아요!</p>
							
							<p >당신이 애완견을 사랑하는 것 처럼<br/>
							엄마가 아이를 사랑으로 키우는 것 처럼</p>
							
							<p >당신의 아이디어로 탄생시킨 앱 또한 <br/>
							정성과 사랑만이 성장 시킬 수 있습니다.</p>
							
							<p >앱! 한번 만들면 끝나는 하루밤의 사랑이 아닙니다.<br/>
							생명체 입니다.<br/>
							탄생에서 성장까지  함께 할 수 있는 그는 …
                      </p>
					</div>
					<a href="#menu7" class="link-scroll"><img src="assets/images/brainyx_images/m_intro_btn.gif" 
							onmouseover="this.src='assets/images/brainyx_images/m_intro_btn_dw.gif';"
							onmouseout="this.src='assets/images/brainyx_images/m_intro_btn.gif';"></a>

                      <div class="content-to-populate-in-modal" id="modal-content-3">
                        <h1>Suspendisse molestie</h1>
                        <p><img data-img-src="assets/images/other_images/transp-image6.png" class="lazy rounded_border hover_effect pull-left" alt="Lorem Ipsum">Etiam at ligula sit amet arcu laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. <a href="#">Suspendisse molestie lorem odio</a>, sit amet. </p>
                        <p>Laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio, sit amet.</p>
                        <p>Suspendisse molestie lorem odio, sit amet. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio test.</p>
                      </div><!-- #modal-content-3 -->
                    </div>
                  </div><!-- .item -->

                  <div class="item">
                    <div class="carousel-text-content">
                      <h6 class="title">가격이 왜 다를까요?</h6>
                      <img src="assets/images/brainyx_images/m_title_line.gif" align="center" style="MARGIN: 10px 0px 2px 0px">
                      <p class="feature-paragraph"></p>
                      <div class= "intro-text" >
                      <p >가방<br />
							명품가방, 짝퉁가방, 수제품<br />
							모두 가방은 가방 입니다.</p>
							
							<p >어플<br />
							어플 만들어 주는 복제 어플<br />
							한가지 틀로 디자인만 바꾸는 어플<br />
							웹사이트를 작게 만든 어플<br />
							모두 어플 입니다.</p>
							
							<p >당신은 싸게 만드는 것이 목적입니까<br />
							목적을 실현해 주는 무언가를 만드십니까</p>
							
							<p >제대로 만들어야 마음도 편안합니다.<br />
							당신의 안목이 가격입니다.
                      </p>
                      </div>
                      <a href="#menu7" class="link-scroll"><img src="assets/images/brainyx_images/m_intro_btn.gif" 
							onmouseover="this.src='assets/images/brainyx_images/m_intro_btn_dw.gif';"
							onmouseout="this.src='assets/images/brainyx_images/m_intro_btn.gif';"></a>

                      <div class="content-to-populate-in-modal" id="modal-content-4">
                        <h1>Maecenas id dolor</h1>
                        <p><img data-img-src="assets/images/other_images/transp-image7.png" class="lazy rounded_border hover_effect pull-left" alt="Lorem Ipsum">Etiam at ligula sit amet arcu laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. <a href="#">Suspendisse molestie lorem odio</a>, sit amet. </p>
                        <p>Laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio, sit amet.</p>
                        <p>Suspendisse molestie lorem odio, sit amet. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio test.</p>
                      </div><!-- #modal-content-4 -->
                    </div>
                  </div><!-- .item -->

                  <div class="item">
                    <div class="carousel-text-content">
                      <h6 class="title">㈜브레닉스</h6>
                      <img src="assets/images/brainyx_images/m_title_line.gif" align="center" style="MARGIN: 10px 0px 2px 0px">
                      <p class="feature-paragraph"></p>
                      <div class="intro-text" >
                      <p >2000년 부터 소프트웨어 개발 회사 입니다.</br >
							국내 유명한 메신저 많이 만들었던 회사 입니다.</br >
							메세징 기술 잘 연구해서 일본의 증권가에서도 인정 받은 회사 입니다.</br >
							정보통신부 장관상 등 많은 인정서도 받았습니다.</br >
							검색해 보시면 흔적들이 보일 것 입니다.</p>
							
							<p >그동안 축척된 모든 기술을 스마트폰에 맞게 바꾸었습니다.</br >
							개발 방법도 바꾸고 연구하여 개발비를 아낄 수 있게 했습니다.</br >
							오래도록 편안 하실 수 있게 개발 이후 체계도 만들었습니다.</p>
							
							<p >그리고</p>
							
							<p >스마트 시대의 고객님들이 소프트웨어 기술자들이 아니기에</br >
							앱개발작가 들이 고객님과 함께 고민해 드립니다.</p>
							
							<p >기술은 잊어 버리시고 당신의 아이디어를 마음껏</br >
							편안하게 작가들과 이야기 하시면 됩니다.
                      </p>
                      </div>
                      <a href="#menu7" class="link-scroll"><img src="assets/images/brainyx_images/m_intro_btn.gif" 
							onmouseover="this.src='assets/images/brainyx_images/m_intro_btn_dw.gif';"
							onmouseout="this.src='assets/images/brainyx_images/m_intro_btn.gif';"></a>

                      <div class="content-to-populate-in-modal" id="modal-content-5">
                        <h1>Sed scelerisque</h1>
                        <p><img data-img-src="assets/images/other_images/transp-image4.png" class="lazy rounded_border hover_effect pull-left" alt="Lorem Ipsum">Etiam at ligula sit amet arcu laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. <a href="#">Suspendisse molestie lorem odio</a>, sit amet. </p>
                        <p>Laoreet consequat. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio, sit amet.</p>
                        <p>Suspendisse molestie lorem odio, sit amet. Duis dictum lorem metus, vitae dapibus risus imperdiet nec. Suspendisse molestie lorem odio test.</p>
                      </div><!-- #modal-content-5 -->
                    </div>
                  </div><!-- .item -->

                </div><!-- .carousel-inner -->

                <!-- Controls -->
                <a class="left carousel-control" href="#features-carousel" data-slide="prev"></a>
                <a class="right carousel-control" href="#features-carousel" data-slide="next"></a>

              </div><!-- #about-carousel -->

          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
        
        
        
        <article id="menu1" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg1.jpg">
          <div class="content-wrapper clearfix">
            <div class="col-sm-11 col-md-10 pull-right">

                <h1 class="section-title" >앱개발 가이드</h1>
                
                <!-- grid -->
                <section class="grid row clearfix clearfix-for-2cols" style="margin: 30px 0px 0px 0px">

                  <!-- grid item -->
                  <div class="grid-item col-md-6" style="margin: 30px 0px 20px 0px">
                    <div class="item-content clearfix">
                      <span class="icon glyphicon-coustom"><img src="assets/images/brainyx_images/menu1_ico01.png" ></span>
                      <div class="text-content" onclick="populate_and_open_modal(event, 'brainyx-modal-content-1');" onmouseover="this.style.cursor='hand'" style="cursor: pointer;">
                        <h5>고객님은 앱이 왜 필요하시나요?</h5>
<!--                         <p>옛날에는 소프트웨어를 기업이나 전문분야 사람들만 만들었습니다. 그런데 왜 요즘은 미용실원장님도, 식당하시는 사장님도 앱을 만들까요 ? 그것은 클라우드 시대가 되었기 때문입니다. -->
<!--                         </p> -->
<!-- 						<p align="center">…</p> -->
                      </div>
                      
                      <div class="content-to-populate-in-modal" id="brainyx-modal-content-1">
	                  <h2>고객님은 앱이 왜 필요하시나요?<p/></h2>
	                  <p>옛날에는 소프트웨어를 기업이나 전문분야 사람들만 만들었습니다.<br />
							그런데 왜 요즘은 미용실원장님도, 식당하시는 사장님도 앱을 만들까요 ?<br /><br />
							
							그것은 클라우드 시대가 되었기 때문입니다.<br />
							무슨 말일까요 ?<br />
							간단히 설명해서 예전과 달리 모든 거래는 현실이 아닌 가상세계 그러니까<br />
							구름 위에서 이루어진다는 말입니다.<br />
							땅 위에서는 구름위에서 거래한 물건들이 배달되는것 뿐이지요.<br /><br />
							
							그래서 누구든지 스마트 세계를 벗어나서 비즈니스를 할 수 없는 세상이<br /> 
							되어 버린 것입니다.<br /><br />
							
							이제 철공소 찾아가서 낫 만드는 시대는 끝났습니다.<br />
							고객님이 어플을 만들어야 겠다고 생각했다면 어플이 목적이 아니라 사업이<br />
							목적이 되어야 합니다.<br /><br />
							
							브레닉스는 철공소가 아닌 소프트웨어 팩토리 까페 입니다.<br />
							고객님의 비즈니스를 도와 드리는 …
						</p>
	                </div><!-- #modal-content-1 -->
                
                    </div><!-- end: .item-content -->
                  </div><!-- end: .grid-item -->

                  <!-- grid item -->
                  <div class="grid-item col-md-6" style="margin: 30px 0px 20px 0px">
                    <div class="item-content clearfix">
                    <span class="icon glyphicon-coustom"><img src="assets/images/brainyx_images/menu1_ico02.png" ></span>
                      <div class="text-content" onclick="populate_and_open_modal(event, 'brainyx-modal-content-2');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                        <h5>앱 개발 잘하는 방법</h5>
<!--                         <p>가장 중요한 것은 적절한 금액으로 만들어야 합니다. 적절한 금액을 들이지 않으면 반드시 문제도 발생 됩니다. 경기가 좋지 않아 적절하지 않은 금액으로 만들 기회도 많은 시대 입니다.</p> -->
<!-- 						<p align="center">…</p> -->
                      </div>
                      
                      <div class="content-to-populate-in-modal" id="brainyx-modal-content-2">
	                  <h2>앱 개발 잘하는 방법<p/></h2>
	                  <p>가장 중요한 것은 적절한 금액으로 만들어야 합니다.<br/>
							적절한 금액을 들이지 않으면 반드시 문제도 발생 됩니다.<br/>
							경기가 좋지 않아 적절하지 않은 금액으로 만들 기회도 많은 시대 입니다.<br/><br/>
							
							적절한 금액은 무엇일까요?<br/>
							아마 100원을 내고 120원 짜리를 받았다는 느낌이 드는것 아닐까요<br/><br/>
							
							브레닉스는 적절한 금액의 개념이 이렇습니다.<br/>
							-예전에는 소프트웨어 개발에서 기술료를 받았으나 이제 받지 않습니다.<br/>
							-개발에 투자되는 인건비만 받습니다.<br/><br/>
							
							그 외에 체크할 항목은…<br/>
							-디자인 (UI, UX)은 괜찮은지<br/>
							-버그 최소화 방법을 갖고 있는지<br/>
							-서버 안정성, 트래픽 처리능력 <br/>
							등을 가지고 있느냐를 판단하시면 되는데<br/>
							주로 업력이 이것을 말해주는 것입니다.
						</p>
	                </div><!-- #modal-content-1 -->
                    </div><!-- end: .item-content -->
                  </div><!-- end: .grid-item -->

                  <!-- grid item -->
                  <div class="grid-item col-md-6">
                    <div class="item-content clearfix">
                      <span class="icon glyphicon-coustom"><img src="assets/images/brainyx_images/menu1_ico03.png" ></span>
                      <div class="text-content" onclick="populate_and_open_modal(event, 'brainyx-modal-content-3');" onmouseover="this.style.cursor='hand'" style="cursor: pointer">
                        <h5>앱 개발 실패 십계명</h5>
<!--                         <p>1. 무조건 싸게 해 줄 곳을 찾는다.<br/> -->
<!-- 								2. 생각하는 기능은 다 넣는다.<br/> -->
<!-- 								3. 관리는 둘째 문제, 만드는 것만 생각한다.<br/> -->
<!-- 								4. 화면기능만 생각하고 서버쪽은 계산하지 않는다. -->
<!--                         </p> -->
<!--                         <p align="center">…</p> -->
                      </div>
                      
                      <div class="content-to-populate-in-modal" id="brainyx-modal-content-3">
	                  <h2>앱 개발 실패 십계명<p/></h2>
	                  <p>1. 무조건 싸게 해 줄 곳을 찾는다.<br/>
							2. 생각하는 기능은 다 넣는다.<br/>
							3. 관리는 둘째 문제, 만드는 것만 생각한다.<br/>
							4. 화면기능만 생각하고 서버쪽은 계산하지 않는다.<br/>
							5. 내 아이디어는 무조건 대박이라고 생각한다.<br/>
							6. 만들기만 하면 구름처럼 다운 받을 것이라 생각한다.<br/>
							7. 홍보도 되기전에 결재 기능 부터 넣는다.<br/>
							8. 로또랑 앱이랑 사촌간이라 생각한다.<br/>
							9. 사업인지 재미인지 구별이 안되는데 만든다.<br/>
							10. 잠수부 에게 개발을 맡긴다.
						</p>
	                </div><!-- #modal-content-1 -->
                    </div><!-- end: .item-content -->
                  </div><!-- end: .grid-item -->

                  <!-- grid item -->
                  <div class="grid-item col-md-6">
                    <div class="item-content clearfix">
                      <span class="icon glyphicon-coustom"><img src="assets/images/brainyx_images/menu1_ico04.png" ></span>
                      <div class="text-content" onclick="populate_and_open_modal(event, 'brainyx-modal-content-4');" onmouseover="this.style.cursor='hand'" style="cursor: pointer">
                        <h5>절대 끝나지 않는 시작</h5>
<!--                         <p>앱을 만들겠다는 생각을 하셨습니까? 만드는 것 자체도 큰일 입니다. 아이디어를 기획으로 정리하고 디자인 하고 기술적인 문제를 해결해야 합니다. 그러나 이것은 시작입니다.</p> -->
<!-- 						<p align="center">…</p> -->
                      </div>
                      
                      <div class="content-to-populate-in-modal" id="brainyx-modal-content-4">
	                  <h2>절대 끝나지 않는 시작<p/></h2>
	                  <p>앱을 만들겠다는 생각을 하셨습니까?<br/>
							만드는 것 자체도 큰일 입니다.<br/>
							아이디어를 기획으로 정리하고<br/>
							디자인 하고 <br/>
							기술적인 문제를 해결해야 합니다.<br/>
							그러나 이것은 시작입니다.<br/><br/>
							
							관리를 위한 기술자는 있습니까?<br/>
							만든 앱을 운영할 서버와 IDC는 준비되어 있습니까?<br/>
							다운로드가 많아지면 트래픽을 감당할 기술적인 대책은 있으신가요?<br/>
							해킹이나 보안 문제에 대응할 시스템은 갖추고 있나요?<br/><br/>
							
							앱을 만드는 것은 그 이후에 감당해야 할 많은 일들이 기다리고 있습니다.<br/>
							그러나 브레닉스에서는 가능합니다.
						</p>
	                </div><!-- #modal-content-1 -->
                    </div><!-- end: .item-content -->
                  </div><!-- end: .grid-item -->

                </section><!-- end: grid -->

            </div><!-- .col-sm-11 -->
          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
        <article id="menu2" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg2.jpg">
          <div class="content-wrapper clearfix">
            <div class="col-sm-11 pull-right">

                <h1 class="section-title">비용절감 프로젝트</h1>
              
                <!-- feature columns -->
                <section class="feature-columns row clearfix">

                  <!-- feature 1 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-6');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico01.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">합리적인 비용으로 개발하기</h5>
<!--                         <p>브레닉스는 오랜 소프트웨어 회사의 기술료를 받지 않으며 합리적인 개념의 실비로 어플을 개발하여 드립니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->
                    
                     <div class="content-to-populate-in-modal" id="brainyx-modal-content-6">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico01.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h2>합리적인 비용으로 개발하기</h2>
                      <p>브레닉스는 오랜 소프트웨어 회사의 기술료를 받지 않으며<br/>
							합리적인 개념의 실비로 어플을 개발하여 드립니다.<br/><br/>
							
							이 개발은 개발 규모를 산정하여 개발비를 결정하고<br/>
							작가들과 협의하여 개발 기획을 완료하고 개발을 진행하는<br/>
							표현적인 브레닉스의 개발방법입니다.<br/><br/>
							
							이 개발은 개발 시작시 대금 지급 방법을 결정 짖고<br/>
							개발에 착수하는 방법입니다.<br/><br/>
							
							따라서<br/><br/>
							
							기존에 운영관리에 대한 인프라를 갖추고 있는 기업이나 개인의<br/> 
							경우에는 이런 방법으로 개발을 진행하고 서버운영 및<br/>
							기술적인 관리도 고객사에서 해결하는 방법입니다.<br/><br/>
							
							따라서 브레닉스에서는 개발만 담당하게 되는 것이며<br/>
							고객 요청시 하자유지보수 계약에 의해서 기본적인 버그대응 및<br/>
							사고 대응을 해 드리는 개발 방법입니다. 
					  </p>
                    </div><!-- #modal-content-7 -->
                  </article>

                  <!-- feature 2 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-7');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico02.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">시작하는 사업 지원하기</h5>
<!--                         <p>어플로 사업을 시작하시고자 하는 분들을 위한 프로그램 입니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->

                    <div class="content-to-populate-in-modal" id="brainyx-modal-content-7">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico02.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h2>시작하는 사업 지원하기</h2>
                      <p>어플로 사업을 시작하시고자 하는 분들을 위한 프로그램 입니다.<br/><br/>

							주로 전문성을 가진 분들이<br/> 관련 분야의 어플을 개발하고자 하는 경우가 많습니다. <br/><br/>
							
							이 경우 개인의 투자로 어플을 만들고자 하는 경우가 많으며<br/> 새로 시작하는 사업이라 무리한 개발비 투자가 어려울 것입니다.<br/><br/>
							
							따라서 본사의 정책 프로그램에 정책 심사에 따라 일부 개발비만을<br/> 납부하시고 수익화 성공 이후에 일정 수익을 나누는 형태의 계약으로<br/> 원하시는 사업을 진행하실 수 있습니다.
					  </p>
                    </div><!-- #modal-content-7 -->
                  </article>

                  <!-- feature 3 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-8');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico03.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">할부로 개발하기 </h5>
<!--                         <p>개발 초기 500만원만 납부 하시면 됩니다. 그 이후 할부로 12개월간 나누어서 개발비를 납부하실 수 있는 프로그램 입니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->

                     <div class="content-to-populate-in-modal" id="brainyx-modal-content-8">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico03.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h2>할부로 개발하기</h2>
                      <p>(1)개발 초기 500만원만 납부 하시면 됩니다.<br/>
							그 이후 할부로 12개월간 나누어서<br/> 개발비를 납부하실 수 있는 프로그램 입니다.<br/><br/>
							
							유지보수비는 별도이며<br/>
							단지 개발비를 분할 상환하시고자 하는 분들에게 적합합니다.<br/><br/>
							
							(2)개발 초기 500만원을 납부 하시면 됩니다.<br/>
							그 이후 12개월, 24개월, 36개월의 프로그램이 있습니다.<br/>
							이 프로그램은 개발 이후 운영관리를 브레닉스에서 맡게 되며<br/>
							매월 1M/M 정도의 수정 및 업그레이드가 있을 경우에는<br/>
							이 계약 조건에 따라 별도의 비용 없이 지속적으로<br/>
							개발 지원을 해 드리는 프로그램 입니다.
							 </p>
                    </div><!-- #modal-content-7 -->
                  </article>

                </section><!-- end: .feature-columns -->
                
                <!-- feature columns -->
                <section class="feature-columns row clearfix">

                  <!-- feature 4 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-9');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico04.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">일부 투자 형태로 개발하기</h5>
<!--                         <p>개발비가 부족할 경우 총 개발 비용의 50%만 납부하시고 나머지는 본사에서 투자하는 형식으로 개발을 하실 수 있습니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->
                    
                     <div class="content-to-populate-in-modal" id="brainyx-modal-content-9">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico04.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h2>일부 투자 형태로 개발하기</h2>
                      <p>개발비가 부족할 경우 총 개발 비용의 50%만 납부하시고<br/>
                      		나머지는 본사에서 투자하는 형식으로 개발을 하실 수 있습니다.<br/><br/>
							
							이 경우 사업성 평가를 자체내에서 거쳐야 합니다.<br/><br/>
							
							심사 통과가 되면 본사의 개발지원은 투자의 일부로 성립되며<br/>
							본사와 지분관계를 형성하는 회사가 됩니다.
   					  </p>
                    </div><!-- #modal-content-7 -->
                  </article>

                  <!-- feature 5 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-10');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico05.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">제대로 개발하기</h5>
<!--                         <p>어플 시대에 맞게 회사의 체질을 변화 시켰으나 기존의 재래식 방식의 기술 개발도 진행합니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->

                    <div class="content-to-populate-in-modal" id="brainyx-modal-content-10">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico05.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h2>제대로 개발하기</h2>
                      <p>어플 시대에 맞게 회사의 체질을 변화 시켰으나 <br/>
                      		기존의 재래식 방식의 기술 개발도 진행합니다.<br/>
							따라서 높은 수준의 기술설계와 제대로된 개발을 원하신다면<br/>
							본사 개발팀을 직업 활용하여 귀사에 필요한 제품을<br/>
							파견, 내부직원활용, BXM멤버 활용등의 방법으로 체계적인 설계와<br/>
							시스템 연동 트래픽 계산에 의한 모든 처리가 가능한<br/>
							예전 방식의 전통적인 소프트웨어 개발도 가능합니다.<br/><br/>
							
							또한 본사에서 개발한 앱 중에 사용자가 많아질 경우<br/>
							이 프로그램으로 개발 작업을 진행하셔야 하는 상황이 발생 될 수 도 있습니다.
                      </p>
                    </div><!-- #modal-content-7 -->
                  </article>

                  <!-- feature 6 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-11');" class="thumbnail linked">
                      <div class="image-container">
                        <img data-img-src="assets/images/brainyx_images/m_prjt_ico06.png" class="lazy item-thumbnail" alt="Lorem Ipsum">
                      </div>
                      <div class="caption">
                        <h5 align="center">무료로 개발하기</h5>
<!--                         <p>본사의 어플 개발 시스템은 기존에 개발사에서 인력 위주로 운영하던 비 효율적인 방식을 개선하여 모든 개발 과정을 체계화 하였습니다.</p> -->
                      </div><!-- .caption -->
                    </a><!-- .thumbnail -->

                    <div class="content-to-populate-in-modal" id="brainyx-modal-content-11">
                      <img data-img-src="assets/images/brainyx_images/m_prjt_detail_ico06.png" class="lazy full-width" alt="Lorem Ipsum">
                      <h1>무료로 개발하기</h1>
                      <p>본사의 어플 개발 시스템은 기존에 개발사에서 인력 위주로 운영하던<br/>
                      		비 효율적인 방식을 개선하여 모든 개발 과정을 체계화 하였습니다.<br/><br/>
							
							그리고 앱 개발에 도움 줄 수 있는 많은 회사와 협력관계를 형성해 놓았으며<br/>
							국내  뿐만 아니라 해외 3개국에 개발 조직이 운영되고 있습니다.<br/><br/>
							
							현 시대에 앱 개발은 비즈니스 입니다.<br/>
							따라서 좋은 아이디어를 가지신 분들이 자금이 부족할 경우<br/>
							또는 홍보의 목적으로 본사에서 운영하는  JTBC  앱으로성공하기 코너에<br/>
							출연 신청 하시면 토너먼트를 통하여<br/>
							심사를 거치기 되면 무료로 개발을 해 드립니다.<br/><br/>
							
							좋은 아이디어를 가지신 분들의 많은 참여 바랍니다.
							</p>
                    </div><!-- #modal-content-7 -->
                  </article>

                </section><!-- end: .feature-columns -->

              <!-- End: Section content to edit -->

            </div><!-- .col-sm-10 -->
          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
        <article id="menu3" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg3.jpg">
          
                    <div class="content-wrapper clearfix" >
                    
		                  <section class="feature-text">
		                  <h1>사업분야</h1>
		                  <p style="MARGIN: 0px 0px 5px 0px" >브레닉스는 사업분야가 없습니다?</p>
		
								<p style="MARGIN: 0px 0px 5px 0px">브레닉스는 고객님들의 아이디어를 가장 편하고 저렴하게 끝까지 책임지면서 실현시켜드리는 소프트웨어 개발 공장 입니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">브레닉스는 2000년 부터 가장 어려운 기술 시장에 뛰어들어 정보통신부 장관상도 받았으며</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">국내에 굴지의 기업으로 부터 러브콜을 받아왔습니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">따라서 가장 어려운 기술 부터 가장 쉬운 홈페이지까지 이제는 모두 다 할 수 있어야 합니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">또한 국내만 300명 이상의 개발자가 피자한조각 나눔 개발 프로젝트에 참여하고 있으므로 어떤 개발도 수용 가능 합니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">고객님은 기술 걱정은 전혀 하지 마시고 앱개발 작가와 상담하시면 모든 문제가 해결 될  것입니다.</p>
								
								<p style="MARGIN: 0px 0px 0px 0px">감사합니다.</p>
		                </section>


          </div><!-- .content-wrapper -->
          
        </article><!-- .section-wrapper -->
        
        
        <article id="menu3_1" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg3_1.jpg">
          
          <div class="content-wrapper clearfix" >
         	 <h1 class="section-title" >보유기술</h1>
         	  <div class="col-sm-11 pull-right" style="MARGIN: 30px 0px 0px 0px">

                <!-- feature columns -->
                <section class="feature-columns row clearfix">

                  <!-- feature 1 -->
                  <article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-3-1', '', 'full-size');" class="thumbnail linked noneThumbnail">
                    	<img src="assets/images/brainyx_images/skill_icon1.png" onmouseover="this.style.cursor='hand'" style="cursor:pointer"/>
                    </a><!-- .thumbnail -->
                  </article>

                  <!-- feature 2 -->
					<article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-3-2', '', 'full-size');" class="thumbnail linked noneThumbnail">
						<img src="assets/images/brainyx_images/skill_icon2.png" onmouseover="this.style.cursor='hand'" style="cursor:pointer"/>
                    </a><!-- .thumbnail -->
                  </article>
                  
                  <!-- feature 3 -->
         			<article class="feature-col col-md-4">
                    <a href="" onclick="populate_and_open_modal(event, 'brainyx-modal-content-3-3', '', 'full-size');" class="thumbnail linked noneThumbnail">
                    	<img src="assets/images/brainyx_images/skill_icon3.png" onmouseover="this.style.cursor='hand'" style="cursor:pointer"/>
                    </a><!-- .thumbnail -->
                  </article>
                  
                  <div class="content-to-populate-in-modal" id="brainyx-modal-content-3-1">
                      <img data-img-src="assets/images/brainyx_images/skill_1.png" class="lazy full-width" alt="Lorem Ipsum">
		          </div><!-- #modal-content-3-1 -->
		          <div class="content-to-populate-in-modal" id="brainyx-modal-content-3-2">
                      <img data-img-src="assets/images/brainyx_images/skill_2.png" class="lazy full-width" alt="Lorem Ipsum">
		          </div><!-- #modal-content-3-1 -->
		          <div class="content-to-populate-in-modal" id="brainyx-modal-content-3-3">
                      <img data-img-src="assets/images/brainyx_images/skill_3.png" class="lazy full-width" alt="Lorem Ipsum">
		          </div><!-- #modal-content-3-1 -->


                </section><!-- end: .feature-columns -->
                

              <!-- End: Section content to edit -->

            </div><!-- .col-sm-10 -->
          </div>
                    
           
          
        </article><!-- .section-wrapper -->
        
        

        <article id="menu4" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg4.jpg">
        
          <div class="content-wrapper clearfix">
        		<h1 class="section-title">공장특징</h1>
		        	<div class="img_center">
		            	<img src="assets/images/brainyx_images/4_feature.png" style="margin: 25px 0px 0px 0px" align="center">
		            </div>        
            <!-- <div id="features-carousel2" class="carousel slide with-title-indicators max-height" data-height-percent="70" data-ride="carousel" style="MARGIN: 60px 0px 0px 0px">
                
                Indicators - slide navigation
                <ol class="carousel-indicators title-indicators">
                  <li data-target="#features-carousel1" data-slide-to="0" class="active">하나</li>
                  <li data-target="#features-carousel1" data-slide-to="1">둘</li>
                  <li data-target="#features-carousel1" data-slide-to="2">셋</li>
                </ol>

                Wrapper for slides
                <div class="carousel-inner">

                  <div class="item active">
                  <img  src="assets/images/brainyx_images/4_feature.png">
                  </div>.item

                </div>.carousel-inner

                Controls
                <a class="left carousel-control" href="#features-carousel1" data-slide="prev"></a>
                <a class="right carousel-control" href="#features-carousel1" data-slide="next"></a>

              </div>#about-carousel -->
            
                      </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        

        <article id="menu5" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg5.jpg">
          <div class="content-wrapper clearfix">
            
              <h1 class="section-title">운영관리</h1>
              	<div class="img_center">
           			<img  src="assets/images/brainyx_images/5_operate-admin.png" style="MARGIN: 25px 0px 0px 0px">
              	</div>
               <!-- <div id="features-carousel3" class="carousel slide with-title-indicators max-height" data-height-percent="70" data-ride="carousel" style="MARGIN: 60px 0px 0px 0px">
                
                Indicators - slide navigation
                <ol class="carousel-indicators title-indicators">
                  <li data-target="#features-carousel1" data-slide-to="0" class="active">하나</li>
                  <li data-target="#features-carousel1" data-slide-to="1">둘</li>
                  <li data-target="#features-carousel1" data-slide-to="2">셋</li>
                </ol>

                Wrapper for slides
                <div class="carousel-inner">

                  <div class="item active">
                  <img  src="assets/images/brainyx_images/5_operate-admin.png">
                  </div>.item

                </div>.carousel-inner

                Controls
                <a class="left carousel-control" href="#features-carousel1" data-slide="prev"></a>
                <a class="right carousel-control" href="#features-carousel1" data-slide="next"></a>

              </div>#about-carousel -->

          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
        <article id="menu6" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg6.jpg">
          <div class="content-wrapper clearfix">
            
              <h1 class="section-title" style="margin: 0px 0px 50px 0px">포트폴리오</h1>
              
              <div id="pnl_tec_1" style="background-color:#aa5a5a5a; height-min:3200px; width:1024px; margin:auto; padding-bottom:30px; visibility:visible; text-align: center; "> 
					<ul style="margin:0 auto; padding: 0px;">
						<li>
						
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-1', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','assets/images/brainyx_images/02PORTFOLIO/images/app_001_ov.png',1)"><!--openLayer('확대이미가 있는 레이어 id명', x축의위치 , y축의위치)-->
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_001.png" id="Image1"/><!--기본작은 썸네일 이미지-->
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-1">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_001img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-2', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image6','','assets/images/brainyx_images/02PORTFOLIO/images/app_006_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_006.png" id="Image6"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-2">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_006img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-3', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','assets/images/brainyx_images/02PORTFOLIO/images/app_010_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_010.png" id="Image10"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-3">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_010img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>

						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-4', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image20','','assets/images/brainyx_images/02PORTFOLIO/images/app_020_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_020.png" id="Image20"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-4">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_020img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-5', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','assets/images/brainyx_images/02PORTFOLIO/images/app_002_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_002.png" id="Image2"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-5">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_002img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-6', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image19','','assets/images/brainyx_images/02PORTFOLIO/images/app_019_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_019.png" id="Image19"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-6">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_019img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>

						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-7', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image4','','assets/images/brainyx_images/02PORTFOLIO/images/app_004_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_004.png" id="Image4"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-7">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_004img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-8', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image8','','assets/images/brainyx_images/02PORTFOLIO/images/app_008_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_008.png" id="Image8"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-8">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_008img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-9', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image9','','assets/images/brainyx_images/02PORTFOLIO/images/app_009_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_009.png" id="Image9"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-9">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_009img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>

						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-10', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image5','','assets/images/brainyx_images/02PORTFOLIO/images/app_005_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_005.png" id="Image5"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-10">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_005img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-11', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image3','','assets/images/brainyx_images/02PORTFOLIO/images/app_003_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_003.png" id="Image3"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-11">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_003img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-12', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image18','','assets/images/brainyx_images/02PORTFOLIO/images/app_018_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_018.png" id="Image18"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-12">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_018img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>
						
						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-13', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image11','','assets/images/brainyx_images/02PORTFOLIO/images/app_011_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_011.png" id="Image11"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-13">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_011img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-16', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image17','','assets/images/brainyx_images/02PORTFOLIO/images/app_017_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_017.png" id="Image17"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-16">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_017img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-15', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','assets/images/brainyx_images/02PORTFOLIO/images/app_007_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_007.png" id="Image7"/>
								</a>
							<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-15">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_007img.png" class="lazy full-width" >
                   			</div>
						</li>
						
						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-19', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image13','','assets/images/brainyx_images/02PORTFOLIO/images/app_013_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_013.png" id="Image13"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-19">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_013img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-23', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','assets/images/brainyx_images/02PORTFOLIO/images/app_024_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_024.png" id="Image24"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-23">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_024img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-21', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image16','','assets/images/brainyx_images/02PORTFOLIO/images/app_016_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_016.png" id="Image16"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-21">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_016img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>
						
						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-22', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','assets/images/brainyx_images/02PORTFOLIO/images/app_023_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_023.png" id="Image23"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-22">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_023img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-26', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image26','','assets/images/brainyx_images/02PORTFOLIO/images/app_026_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_026.png" id="Image26"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-26">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_026img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-24', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image15','','assets/images/brainyx_images/02PORTFOLIO/images/app_015_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_015.png" id="Image15"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-24">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_015img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>
						
						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-25', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','assets/images/brainyx_images/02PORTFOLIO/images/app_025_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_025.png" id="Image25"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-25">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_025img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-32', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','assets/images/brainyx_images/02PORTFOLIO/images/app_033_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_033.png" id="Image33"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-32">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_033img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-27', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image27','','assets/images/brainyx_images/02PORTFOLIO/images/app_027_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_027.png" id="Image27"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-27">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_027img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>
						
						</li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-28', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','assets/images/brainyx_images/02PORTFOLIO/images/app_028_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_028.png" id="Image28"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-28">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_028img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-35', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','assets/images/brainyx_images/02PORTFOLIO/images/app_036_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_036.png" id="Image36"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-35">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_036img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-30', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','assets/images/brainyx_images/02PORTFOLIO/images/app_031_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_031.png" id="Image31"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-30">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_031img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>


						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-31', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','assets/images/brainyx_images/02PORTFOLIO/images/app_032_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_032.png" id="Image32"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-31">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_032img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-38', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image39','','assets/images/brainyx_images/02PORTFOLIO/images/app_039_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_039.png" id="Image39"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-38">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_039img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-33', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','assets/images/brainyx_images/02PORTFOLIO/images/app_034_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_034.png" id="Image34"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-33">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_034img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>
						

						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-34', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','assets/images/brainyx_images/02PORTFOLIO/images/app_035_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_035.png" id="Image35"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-34">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_035img.png" class="lazy full-width" >
                    			</div>
							</span>
							
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-39', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image40','','assets/images/brainyx_images/02PORTFOLIO/images/app_040_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_040.png" id="Image40"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-39">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_040img.png" class="lazy full-width" >
                    			</div>
							</span>

							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-36', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','assets/images/brainyx_images/02PORTFOLIO/images/app_037_ov.png',1)">
									<img style="border:none; margin:40px 0px 0px 40px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_037.png" id="Image37"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-36">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_037img.png" class="lazy full-width" >
                    			</div>
							</span>
						</li>

						<li>
							<span>
								<a onmouneover="this.style.cursor='hand'" style="cursor: pointer;" onclick="populate_and_open_modal(event, 'brainyx-modal-content-por-37', '', 'full-size');" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image38','','assets/images/brainyx_images/02PORTFOLIO/images/app_038_ov.png',1)">
									<img style="border:none; margin:40px 40px 0px 0px;" src="assets/images/brainyx_images/02PORTFOLIO/images/app_038.png" id="Image38"/>
								</a>
								<div class="content-to-populate-in-modal" id="brainyx-modal-content-por-37">
                      				<img data-img-src="assets/images/brainyx_images/02PORTFOLIO/images/app_038img.png" class="lazy full-width" >
                    			</div>
							</span>
							



						</li>
					</ul>

				</div>

             	
                

          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
       	<article id="menu7" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg7.jpg">
          <div class="content-wrapper clearfix">
            
              <h1 class="section-title">기술력</h1>
              
              <div id="features-carousel4" class="carousel slide with-title-indicators max-height" data-height-percent="70" data-ride="carousel" style="MARGIN: 60px 0px 0px 0px">
                
                <!-- Indicators - slide navigation -->
                <!-- <ol class="carousel-indicators title-indicators">
                  <li data-target="#features-carousel1" data-slide-to="0" class="active">하나</li>
                  <li data-target="#features-carousel1" data-slide-to="1">둘</li>
                  <li data-target="#features-carousel1" data-slide-to="2">셋</li>
                </ol> -->

                <!-- Wrapper for slides -->
                <div class="carousel-inner">

                  <div class="item active">
                  <img  src="assets/images/brainyx_images/7.technical-skills_1.png" onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-1');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->
                  
                  <div class="item">
                  <img  src="assets/images/brainyx_images/7.technical-skills_2.png" onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-2');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->
                  
				  <div class="item">
                  <img  src="assets/images/brainyx_images/7.technical-skills_3.png"onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-3');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->
                  
                  <div class="item">
                  <img  src="assets/images/brainyx_images/7.technical-skills_4.png"onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-4');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->
                  
                  <div class="item">
                  <img  src="assets/images/brainyx_images/7.technical-skills_5.png"onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-5');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->
                  
                  <div class="item">
                  <img  src="assets/images/brainyx_images/7.technical-skills_6.png"onclick="populate_and_open_modal(event, 'brainyx-modal-content-7-6');" onmouneover="this.style.cursor='hand'" style="cursor: pointer;">
                  </div><!-- .item -->

                </div><!-- .carousel-inner -->

                <!-- Controls -->
                <a class="left carousel-control" href="#features-carousel4" data-slide="prev"></a>
                <a class="right carousel-control" href="#features-carousel4" data-slide="next"></a>

              </div><!-- #about-carousel -->
              
              <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-1" >
                      <h3>일본 마루하치증권 주식거래시스템</h3>
                      <p>2005년<br/><br/>
                      앱이 정상 작동하는지를 실시간 체크하는 로봇이 가동되며<br/><br/>
                      일본 나고야 소재 증권사에서 인터넷을 통한 주식 거래<br/><br/>
                      솔루션을 종합적으로 개발<br/><br/>
                      개발툴 : 델파이, JAVA 등<br/><br/>
                      구성 : 서버(xTS엔진), 관리자(웹), 사용자(PC버전)<br/><br/>
                      핵심기술 : 실시간 메세징, 분산처리, 멤신저, 차트 등
					  </p>
					  <img data-img-src="assets/images/brainyx_images/7.technical-skills_1_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-1 -->
              <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-2">
              		  <h3>일본 CKS증권 서비스 주식회사</h3>
                      <p>2007년<br/><br/>
                      PC기반 WEB버전<br/><br/>
                      일본의 증권 시세를 각 증권사에 공급하는 초고속 실시간 정보 공급 서버 개발<br/><br/>
                      6개월간 기술 검증 받음<br/><br/>
                      구성 : 서버(xTS엔진), 사용자(웹)<br/><br/>
                      핵심기술 : 실시간 메세징, 분산처리, 차트 등
					  </p>
                      <img data-img-src="assets/images/brainyx_images/7.technical-skills_2_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-2 -->
          	  <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-3">
          	  		  <h3>동원증권 주식거래 시스템</h3>
                      <p>2003년<br/><br/>
                      PC용 Client/Server 환경 주가정보 공급용 HTS<br/><br/>
                      동원증권의 기존 시스템의 문제를 모두 기술적으로 정리하고 <br/><br/>
                      HTS 평가에서 단번에 3위로 성장<br/><br/>
                      예전 증권사 광고 중 "모두가 아니라고 할 때 예..."<br/><br/>
                      라고 하는 광고의 모티부가 된 혁신적인 작업<br/><br/>
                      구성 : 서버(xTS엔진), 사용자(C/S기반)<br/><br/>
                      핵심기술 : 소켓통신, 인터페이스 설계 기술, 차트
					  </p>
                      <img data-img-src="assets/images/brainyx_images/7.technical-skills_3_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-3 -->
          	  <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-4">
					  <h3>미스리메신저</h3>
                      <p>1998년 ~ 현재<br/><br/>
                      1998년부터 2003년까지 이슈가 된 국내 최고의 메신저<br/><br/>
                      현재까지 증권가 정보지의 핵심 통신 수단으로 활용됨<br/><br/>
                      가입자 100만명 1일 사용자 7만 유저<br/><br/>
                      구성 : 서버(xTS엔진), 사용자(C/S기반)<br/><br/>
                      핵심기술 : 소켓통신, 분산처리, 고속통신 기술 적용
					  </p>
                      <img data-img-src="assets/images/brainyx_images/7.technical-skills_4_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-4 -->
          	  <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-5">
          	  		  <h3>UC-Station</h3>
                      <p>2009년<br/><br/>
                      서버 1대로 1만 유저까지 수용 가능한 기업용 메신저 패키지<br/><br/>
                      1998년부터 발전되어 왔으며 국내의 500 기업 점유율 20%를<br/><br/>
                      점유하고 있는 기업용 메신저의 대명사<br/><br/>
                      구성 : 서버(xTS엔진), 사용자(C/S기반)<br/><br/>
                      핵심기술 : 소켓통신, 분산처리, 고속통신 기술 적용<br/><br/>
                      특징 : 패키지화, 각종 솔루션 연동 구조 설계
					  </p>
                      <img data-img-src="assets/images/brainyx_images/7.technical-skills_5_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-5 -->
          	  <div class="content-to-populate-in-modal" id="brainyx-modal-content-7-6">
          	  		  <h3>xTS 엔진</h3>
                      <p>2000년 ~ 현재<br/><br/>
                      기업에서 필요한 저비용 서버 환경에서 <br/><br/>
                      대용량, 실시간, 고속전송, 분산처리가 가능한 실시간 메세징 엔진<br/><br/>
                      주식거래용 서버 및 메세징 솔루션 서버로 활용 가능<br/><br/>
                      10여년간 발전되어온 서버엔진으로서 고속전송, 분산처리 각종 서버 역활 등...
					  </p>
                      <img data-img-src="assets/images/brainyx_images/7.technical-skills_6_image.png" class="lazy full-width" alt="Lorem Ipsum" >
          	  </div><!-- #modal-content-7-6 -->
          	  

          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
       	<article id="menu8" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg8.jpg">
          <div class="content-wrapper clearfix">
            
              <h1 class="section-title">회사소개</h1>
              <div class="img_center">
              	<img  src="assets/images/brainyx_images/8.intro.png" style="MARGIN: 25px 0px 0px 0px">
			  </div>
          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
        
               	<article id="menu9" class="section-wrapper clearfix" data-custom-background-img="assets/images/brainyx_images/bg9.jpg">
          <div class="content-wrapper clearfix">
              <h1 class="section-title">1472시리즈</h1>
              <section class="feature-text">
		                  <p style="MARGIN: 0px 0px 5px 0px" >1472시리즈는 브레닉스에서 생활밀착형 어플들을 가끔 개발하여</p>
		
								<p style="MARGIN: 0px 0px 5px 0px">오픈하는 본사 직접 개발 어플들을 소개하는 곳입니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">그 동안 인기를 많이 누렸으나 관리 인력이 부족하에 제대로</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">성장시키지 못한 앱들이 많이 있습니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">혹시 운영해 보시고자 하는 분들은 신청해 주시면 사업권을</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">부여해 드릴 수 있습니다.</p>
								
								<p style="MARGIN: 0px 0px 5px 0px">자세한 1472 시리즈를 보시려면</p>
								
								<p style="MARGIN: 0px 0px 15px 0px"><a href="http://www.1472.net">www.1472.net</a> 으로 이동하시기 바랍니다.</p>
								
								<p style="MARGIN: 0px 0px 0px 0px">감사합니다.</p>
								
		                </section>

          </div><!-- .content-wrapper -->
        </article><!-- .section-wrapper -->
      

      </section><!-- #main-content -->

      <!-- Footer -->
      <section id="footer">

        <!-- Go to Top -->
        <div id="go-to-top" onclick="scroll_to_top();"><span class="icon glyphicon glyphicon-chevron-up"></span></div>

        <ul class="social-icons">
          <li><a href="#" target="_blank" title="Facebook"><img src="assets/images/theme_images/social_icons/facebook.png" alt="Facebook"></a></li>
          <li><a href="#" target="_blank" title="Twitter"><img src="assets/images/theme_images/social_icons/twitter.png" alt="Twitter"></a></li>
          <li><a href="#" target="_blank" title="Google+"><img src="assets/images/theme_images/social_icons/googleplus.png" alt="Google+"></a></li>
        </ul>

        <!-- copyright text -->
        <div class="footer-text-line">&copy; (주)브레닉스 Brainyx</div>
      </section>
      <!-- end: Footer -->      

    </div><!-- #outer-container -->
    <!-- end: Outer Container -->

    <!-- Modal -->
    <!-- DO NOT MOVE, EDIT OR REMOVE - this is needed in order for popup content to be populated in it -->
    <div class="modal fade" id="common-modal" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <div class="modal-body">
          </div><!-- .modal-body -->
        </div><!-- .modal-content -->
      </div><!-- .modal-dialog -->
    </div><!-- .modal -->    

    <!-- Javascripts
    ================================================== -->

    <!-- Jquery and Bootstrap JS -->
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="assets/js/jquery-1.11.1.min.js"><\/script>')</script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>

    <!-- Easing - for transitions and effects -->
    <script src="assets/js/jquery.easing.1.3.js"></script>

    <!-- background image strech script -->
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <!-- background image fix for IE 9 or less
       - use same background as set above to <body> -->
    <!--[if lt IE 9]>
    <script type="text/javascript">
    $(document).ready(function(){
      jQuery("#outer-background-container").backstretch("assets/images/other_images/bg5.jpg");
    });
    </script> 
    <![endif]-->  

    <!-- detect mobile browsers -->
    <script src="assets/js/detectmobilebrowser.js"></script>

    <!-- owl carousel js -->
    <script src="assets/js/owl-carousel/owl.carousel.min.js"></script>

    <!-- lightbox js -->
    <script src="assets/js/lightbox/js/lightbox.min.js"></script>

    <!-- intro animations -->
    <script src="assets/js/wow/wow.min.js"></script>

    <!-- Custom functions for this theme -->
    <script src="assets/js/functions.js"></script>
    <script src="assets/js/initialise-functions.js"></script>

    <!-- IE9 form fields placeholder fix -->
    <!--[if lt IE 9]>
    <script>contact_form_IE9_placeholder_fix();</script>
    <![endif]-->  

  </body>
</html>