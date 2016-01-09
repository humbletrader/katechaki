<fo:table-cell 	number-columns-spanned="${colspan}"
				padding-left="3pt"
				padding-right="3pt"
				padding-top="1pt"
				padding-bottom="1pt"
				border-top-style="solid"
				border-bottom-style="solid"
				border-left-style="solid"
				border-right-style="solid">				 									
		 			<fo:block 	color="white"
		 						font-family="ArialUnicodeMS" 
								font-size="12pt"
								font-style="normal" 
								font-weight="normal"
								margin-left="10pt"
								text-align="${horizAlign.foCode}"
								display-align="${vertAlign.foCode}"
								>
						${value}
					</fo:block>
</fo:table-cell>