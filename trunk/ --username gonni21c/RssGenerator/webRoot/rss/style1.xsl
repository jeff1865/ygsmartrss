<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
     xmlns:dc="http://purl.org/dc/elements/1.1/" version="1.0">
<!-- rssfeed.xsl from http://ajamyajax.com -->
<!-- note: supports most rss feed formats but not all -->

<xsl:output method="html"/>
<xsl:template match="rss/channel">
  <div class="rssdoctitle">
    <a href="{link}"><xsl:value-of select="title"/></a> <br/>
    <xsl:value-of select="description" disable-output-escaping="yes"/> <br/>
    <xsl:if test="pubDate">
      <xsl:value-of select="pubDate"/> <br/>
    </xsl:if>
    <xsl:if test="dc:date">
      <xsl:value-of select="dc:date"/> <br/>
    </xsl:if>
    <xsl:if test="updated">
      Updated: <xsl:value-of select="updated"/> <br/>
    </xsl:if>
  </div>

  <div class="rssitems">
  <xsl:for-each select="item">
    <a href="{link}"><xsl:value-of select="title"/></a> <br/>
    <xsl:value-of select="description" disable-output-escaping="yes"/> <br/>
    <xsl:if test="dc:creator">
      By: <xsl:value-of select="dc:creator"/> <br/>
    </xsl:if>
    <xsl:if test="pubDate">
      <div class="rsspubdate"> <xsl:value-of select="pubDate"/> </div> <br/>
    </xsl:if>
    <xsl:if test="dc:date">
      <div class="rsspubdate">Posted: <xsl:value-of select="dc:date"/> </div> <br/>
    </xsl:if>
    <xsl:if test="updated">
      Updated: <xsl:value-of select="updated"/> <br/>
    </xsl:if>
  </xsl:for-each>
  </div>
</xsl:template>
</xsl:stylesheet>