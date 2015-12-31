## Introduction ##
본 프로젝트는 개인형 정보추천 에이전트의 일부 Component 개발을 위한 연구개발이 목적입니다.
일반 사이트의 정보를 분석해서 신규정보를 추출하고 추출된 정보에 대한 사용자의 선호도를 분석, 사용자의 관심사를 파악하고 사용자가 관심을 가질 만한 정보를 적시에 알려주는 Smart RSS Engine이라 할 수 있습니다. 유사관심사를 가진 사용자간 특정 기사나 웹페이지의 내용을 토대로 PUSH 기반의 커뮤니티 서비스를 제공하는 것을 최종목표로 하고 있습니다.
실시간 정보 추출에 관한 내용은 개발자 블로그( http://jakarta.tistory.com )을 참조해 주십시오.

## Features ##
다음과 같은 기능이 포함될 수 있습니다.
  * 실시간 Web Crawling
  * 웹페이지의 정보 영역 군집 추출을 위한 Wrapper, 웹페이지 필터링
  * 확률기반 사용자 관심사 분석
  * 실시간 정보에 대한 RSS 작성기

## Design & Structure ##
<img src='http://ygsmartrss.googlecode.com/files/smarRss.jpg' />

## Environment ##

  * Spring Dynamic Module(OSGi + Spring)
  * myBatis, mySQL
  * [YGHtmlParser](http://code.google.com/p/yghtmlparser)
  * YGHtmlParserUtil
  * [YGHttpServerCore](http://code.google.com/p/yghttpserver)
  * 한글형태소 분석기(연구용 KMA 예정)

## Developer ##
> Name : Young-Gon Kim, Korea<br>
<blockquote>Email : gonni21c@gmail.com</blockquote>

<h2>Demo</h2>
N/A yet<br>
<br>
<h2>Reference</h2>
- Application Info<br>
<ul><li><a href='http://jakarta.tistory.com/14'>http://jakarta.tistory.com/14</a></li></ul>

- Blog<br>
<ul><li><a href='http://jakarta.tistory.com'>http://jakarta.tistory.com</a> (Korean)<br>
</li><li><a href='http://ygonni.blogspot.com'>http://ygonni.blogspot.com</a> (English)