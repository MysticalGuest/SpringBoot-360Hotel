// Java后台使用EL传值给JS文件

var profitPerDayListThisWeek;//方案一：全局变量

var profitPerDayListLastWeek;

//上月
var JSONListLastMonth;

//本月
var JSONListThisMonth;

//今年每季度
var profitPerQuarterListThisYear;

//近几年年份
var yearList;

//近几年营业额
var profitPerYearList;

$(function(){
    /*===两周对比===*/
    var doubleWeeks = echarts.init(document.getElementById('doubleWeeks'));
    option = {
        title : {
            text:"",
            x:'center',
            y:'top',
            textStyle:
            {
                color:'black',
                fontSize:13
            }
        },
        tooltip : {
            trigger: 'axis'
        },
        grid: {
            left: '3%',
            right: '8%',
            bottom: '5%',
            top:"13%",
            containLabel: true
        },
        color:["#72b332",'#35a9e0'],
        legend: {
            data:['上周','本周'],
            show:true,

            right:'15%',
            y:"0",
            textStyle:{
                color:"#999",
                fontSize:'13'
            },
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['周一','周二','周三','周四','周五','周六','周日'],
                splitLine:{
                    show:true,
                    lineStyle:{
                        color: '#2d3b53'
                    }
                },
                axisLabel:{
                    textStyle:{
                        color:"black"
                    },
                    alignWithLabel: true,
                    interval:0,
                    rotate:'15'
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                splitLine:{
                    show:true,
                    lineStyle:{
                        color: '#2d3b53'
                    }
                },
                axisLabel:{
                    textStyle:{
                        color:"#999"
                    }
                },
            }
        ],
        series : [
            {
                name:'上周',
                type:'line',
                smooth:true,
                symbol:'roundRect',
                data:profitPerDayListLastWeek
            },
            {
                name:'本周',
                type:'line',
                smooth:true,
                symbol:'roundRect',
                data:profitPerDayListThisWeek
            }
        ]
    };
    doubleWeeks.setOption(option);

   
    /*  ===上月营业额对比===*/
    var lastMonth = echarts.init(document.getElementById('lastMonth'));
    option = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            right: '0',
            y:'middle',
            textStyle:{
                color:"black"
            },

            formatter:function(name){
                var oa = option.series[0].data;
                var num = oa[0].value + oa[1].value + oa[2].value + oa[3].value;
                for(var i = 0; i < option.series[0].data.length; i++){
                    if(name==oa[i].name){
                        return name +  ' '+oa[i].value;
                    }
                }
            },
            data: ['第1周','第2周','第3周','第4周']
        },
        series : [
            {
                name: '上月',
                type: 'pie',
                radius : '45%',
                color:['#27c2c1','#9ccb63','#fcd85a','#60c1de'],
                center: ['38%', '50%'],
                data:JSONListLastMonth,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                itemStyle: {
                    normal: {
                        label:{
                            show: true,
                            position:'outside',
                            formatter: '{b}'
                        }
                    },
                    labelLine :{show:true}
                }
            }
        ]
    };
    lastMonth.setOption(option);

    /* ====本月营业额对比====*/
    var thisMonth = echarts.init(document.getElementById('thisMonth'));
    option = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            right: '0',
            y:'middle',
            textStyle:{
                color:"black"
            },
            data: ['第1周','第2周','第3周','第4周'],
            formatter:function(name){
                var oa = option.series[0].data;
                var num = oa[0].value + oa[1].value + oa[2].value + oa[3].value;
                for(var i = 0; i < option.series[0].data.length; i++){
                    if(name==oa[i].name){
                        return name +  ' '+oa[i].value;
                    }
                }
            }
        },
        series : [
            {
                name: '本月',
                type: 'pie',
				color:['#70a3ff','pink','#fcd85a','#60c1de'],
                radius : '60%',
                center: ['35%', '50%'],
                data:JSONListThisMonth,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                itemStyle: {
                    normal: {
                        label:{
                            show: true,
                            position:'outside',
                            formatter: '  {b}'
                        }
                    },
                    labelLine :{show:true}
                }
            }
        ]
    };
    thisMonth.setOption(option);
	
	/*===本季度营业额比较===*/
	var quarter = echarts.init(document.getElementById('quarter'));
	option = {
	    title : {
	        text:"",
	        x:'center',
	        y:'top',
	        textStyle:
	        {
	            color:'#fff',
	            fontSize:13
	        }
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    grid: {
	        left: '3%',
	        right: '8%',
	        bottom: '5%',
	        top:"13%",
	        containLabel: true
	    },
	    color:['#35a9e0'],
	    legend: {
	        data:['这个月每季度'],
	        show:true,
	
	        right:'15%',
	        y:"0",
	        textStyle:{
	            color:"#999",
	            fontSize:'13'
	        },
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : ['第1季度','第2季度','第3季度','第4季度'],
	            splitLine:{
	                show:true,
	                lineStyle:{
	                    color: '#2d3b53'
	                }
	            },
	            axisLabel:{
	                textStyle:{
	                    color:"black"
	                },
	                alignWithLabel: true,
	                interval:0,
	                rotate:'15'
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            splitLine:{
	                show:true,
	                lineStyle:{
	                    color: '#2d3b53'
	                }
	            },
	            axisLabel:{
	                textStyle:{
	                    color:"#999"
	                }
	            },
	        }
	    ],
	    series : [
	        {
	            name:'这个月每季度',
	            type:'line',
	            smooth:true,
	            symbol:'roundRect',
	            data:profitPerQuarterListThisYear
	        }
	    ]
	};
	quarter.setOption(option);
 
    /*===近年营业额比较===*/
    var changedetail = echarts.init(document.getElementById('changedetail'));
    option = {
        tooltip: {
            trigger: 'axis',
            formatter: '{b}</br>{a}: {c}</br>{a1}: {c1}'
        },
        toolbox: {
            show:false,
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        grid:{
            top:'18%',
            right:'5%',
            bottom:'8%',
            left:'5%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: yearList,
                splitLine:{
                    show:false,
                    lineStyle:{
                        color: '#3c4452'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel:{
                    textStyle:{
                        color:"black"
                    },
                    lineStyle:{
                        color: '#519cff'
                    },
                    alignWithLabel: true,
                    interval:0
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '营业额',
                nameTextStyle:{
                    color:'black'
                },
                splitLine:{
                    show:true,
                    lineStyle:{
                        color: '#23303f'
                    }
                },
                axisLine: {
                    show:false,
                    lineStyle: {
                        color: '#115372'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLabel:{
                    textStyle:{
                        color:"black"
                    },
                    alignWithLabel: true,
                    interval:0

                }
            }
        ],
        color:"yellow",
        series: [
            {
                name:'test1',
                type:'bar',
                data:profitPerYearList,
                boundaryGap: '45%',
                barWidth:'40%',

                itemStyle: {
                    normal: {
                        color: function(params) {
                            var colorList = [
                                '#6bc0fb','#7fec9d','#fedd8b','#ffa597','#84e4dd'
                            ];
                            return colorList[params.dataIndex]
                        },label: {
                            show: true,
                            position: 'top',
                            formatter: '{c}'
                        }
                    }
                }
            }
        ]
    };
    changedetail.setOption(option);

})