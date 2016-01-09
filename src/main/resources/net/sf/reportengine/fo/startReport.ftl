<?xml version="1.0" encoding="utf-8"?>
<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
<fo:layout-master-set>
			<fo:simple-page-master 	master-name="report" 
        						page-height="${format.pageSize.height}mm" 
        						page-width="${format.pageSize.width}mm" 
        						margin-top="20mm" 
        						margin-bottom="20mm" 
        						margin-left="20mm" 
        						margin-right="20mm">
          		<fo:region-body/>
        	</fo:simple-page-master>
			
		</fo:layout-master-set>
      
      
      	<fo:page-sequence master-reference="report">
      		<fo:flow flow-name="xsl-region-body">
				<#-- this is for later use
				<fo:block 	font-family="ArialUnicodeMS" 
						font-size="24pt" 
						font-style="normal" 
						space-after="5mm"
						text-align="center">
					Title goes here
          		</fo:block> 
          		-->