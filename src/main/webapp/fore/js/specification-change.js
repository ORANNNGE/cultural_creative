$(function(){
/*————————楹联成品库————————*/
	$('.coupm-speci').click(function(){
		$('.coupletm-norm-bg').show()
		$('.couplet-more').css('position','fixed')
	})
	$('.coupletm-blank').click(function(){
		$('.coupletm-norm-bg').hide()
		$('.couplet-more').css('position','relative')
	})
	//选择尺寸
	/*$('.norm-size').find('p').toggle(function(){
		$('.norm-size').find('p').attr('class',' ')
		$('.norm-size').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.norm-size').find('p').eq($(this).index()).attr('class',' ')
	})*/
	//选择专利产品春联框
	$('.norm-patent').find('p').toggle(function(){
		$('.norm-patent').find('p').attr('class',' ')
		$('.norm-patent').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.norm-patent').find('p').eq($(this).index()).attr('class',' ')
	})
	//选择制作工艺
	$('.norm-crafts').find('p').toggle(function(){
		$('.norm-crafts').find('p').attr('class',' ')
		$('.norm-crafts').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.norm-crafts').find('p').eq($(this).index()).attr('class',' ')
	})
	
/*————————楹联词库-————————*/
	//选择字体或书法家
	$('.thesam-font').click(function(){
		$('.thesarusm-norm-bg').show()
		$('.thesaurus-more').css('position','fixed')
		$('.thesarusm-font').show()
		$('.thesarusm-patent').hide()
		$('.thesarusm-production').hide()
		$('.thesarusm-size').hide()
	})
	$('.thsm-fo').find('p').toggle(function(){
		$('.thsm-fo').find('p').attr('class','')
		$('.thsm-fo').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.thsm-fo').find('p').eq($(this).index()).attr('class',' ')
	})
	$('.thsm-ca').find('p').toggle(function(){
		$('.thsm-ca').find('p').attr('class','')
		$('.thsm-ca').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.thsm-ca').find('p').eq($(this).index()).attr('class',' ')
	})
	//选择尺寸
	$('.thesam-size').click(function(){
		$('.thesarusm-norm-bg').show()
		$('.thesaurus-more').css('position','fixed')
		$('.thesarusm-size').show()
		$('.thesarusm-patent').hide()
		$('.thesarusm-production').hide()
		$('.thesarusm-font').hide()
	})
	$('.thsm-si').find('p').toggle(function(){
		$('.thsm-si').find('p').attr('class','')
		$('.thsm-si').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.thsm-si').find('p').eq($(this).index()).attr('class',' ')
	})
	//选择专利产品春联框
	$('.thesam-patent').click(function(){
		$('.thesarusm-norm-bg').show()
		$('.thesaurus-more').css('position','fixed')
		$('.thesarusm-patent').show()
		$('.thesarusm-font').hide()
		$('.thesarusm-production').hide()
		$('.thesarusm-size').hide()
	})
	$('.thsm-pate').find('p').toggle(function(){
		$('.thsm-pate').find('p').attr('class','')
		$('.thsm-pate').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.thsm-pate').find('p').eq($(this).index()).attr('class',' ')
	})
	//选择制作工艺
	$('.thesam-production').click(function(){
		$('.thesarusm-norm-bg').show()
		$('.thesaurus-more').css('position','fixed')
		$('.thesarusm-production').show()
		$('.thesarusm-patent').hide()
		$('.thesarusm-font').hide()
		$('.thesarusm-size').hide()
	})
	$('.thsm-prod').find('p').toggle(function(){
		$('.thsm-prod').find('p').attr('class','')
		$('.thsm-prod').find('p').eq($(this).index()).attr('class','checked')
	},function(){
		$('.thsm-prod').find('p').eq($(this).index()).attr('class',' ')
	})
	$('.thesarusm-blank').click(function(){
		$('.thesarusm-norm-bg').hide()
		$('.thesaurus-more').css('position','relative')
	})
	
})
