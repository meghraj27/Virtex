/**
 * INSPINIA - Responsive Admin Theme
 *
 * Main controller.js file
 * Define controllers with data used in Inspinia theme
 *
 *
 */

/**
 * MainCtrl - controller
 * Contains several global data used in different view
 *
 */
function MainCtrl($rootScope, $http) {

    var self = this;
    /**
     * countries - Used as duallistbox in form advanced view
     */

    this.countries = [
        {name: 'Amsterdam'},
        {name: 'Washington'},
        {name: 'Sydney'},
        {name: 'Cairo'},
        {name: 'Beijing'}];

    this.getLocation = function (val) {
        return $http.get('//maps.googleapis.com/maps/api/geocode/json', {
            params: {
                address: val,
                sensor: false
            }
        }).then(function (response) {
            return response.data.results.map(function (item) {
                return item.formatted_address;
            });
        });
    };

    /**
     * daterange - Used as initial model for data range picker in Advanced form view
     */
    this.daterange = {startDate: null, endDate: null};

    /**
     * slideInterval - Interval for bootstrap Carousel, in milliseconds:
     */
    this.slideInterval = 5000;

    /**
     * tags - Used as advanced forms view in input tag control
     */

    this.tags = [
        {text: 'Amsterdam'},
        {text: 'Washington'},
        {text: 'Sydney'},
        {text: 'Cairo'},
        {text: 'Beijing'}
    ];

    /**
     * states - Data used in Advanced Form view for Chosen plugin
     */
    this.states = [
        'Alabama',
        'Alaska',
        'Arizona',
        'Arkansas',
        'California',
        'Colorado',
        'Connecticut',
        'Delaware',
        'Florida',
        'Georgia',
        'Hawaii',
        'Idaho',
        'Illinois',
        'Indiana',
        'Iowa',
        'Kansas',
        'Kentucky',
        'Louisiana',
        'Maine',
        'Maryland',
        'Massachusetts',
        'Michigan',
        'Minnesota',
        'Mississippi',
        'Missouri',
        'Montana',
        'Nebraska',
        'Nevada',
        'New Hampshire',
        'New Jersey',
        'New Mexico',
        'New York',
        'North Carolina',
        'North Dakota',
        'Ohio',
        'Oklahoma',
        'Oregon',
        'Pennsylvania',
        'Rhode Island',
        'South Carolina',
        'South Dakota',
        'Tennessee',
        'Texas',
        'Utah',
        'Vermont',
        'Virginia',
        'Washington',
        'West Virginia',
        'Wisconsin',
        'Wyoming'
    ];

    /**
     * check's - Few variables for checkbox input used in iCheck plugin. Only for demo purpose
     */
    this.checkOne = true;
    this.checkTwo = true;
    this.checkThree = true;
    this.checkFour = true;

    /**
     * knobs - Few variables for knob plugin used in Advanced Plugins view
     */
    this.knobOne = 75;
    this.knobTwo = 25;
    this.knobThree = 50;

    /**
     * Variables used for Ui Elements view
     */
    this.bigTotalItems = 175;
    this.bigCurrentPage = 1;
    this.maxSize = 5;
    this.singleModel = false;
    this.radioModel = 'Middle';
    this.checkModel = {
        left: false,
        middle: true,
        right: false
    };

    /**
     * groups - used for Collapse panels in Tabs and Panels view
     */
    this.groups = [
        {
            title: 'Dynamic Group Header - 1',
            content: 'Dynamic Group Body - 1'
        },
        {
            title: 'Dynamic Group Header - 2',
            content: 'Dynamic Group Body - 2'
        }
    ];

    /**
     * alerts - used for dynamic alerts in Notifications and Tooltips view
     */
    this.alerts = [
        {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
        {type: 'success', msg: 'Well done! You successfully read this important alert message.'},
        {type: 'info', msg: 'OK, You are done a great job man.'}
    ];

    /**
     * addAlert, closeAlert  - used to manage alerts in Notifications and Tooltips view
     */
    this.addAlert = function () {
        this.alerts.push({msg: 'Another alert!'});
    };

    this.closeAlert = function (index) {
        this.alerts.splice(index, 1);
    };

    /**
     * randomStacked - used for progress bar (stacked type) in Badges adn Labels view
     */
    this.randomStacked = function () {
        this.stacked = [];
        var types = ['success', 'info', 'warning', 'danger'];

        for (var i = 0, n = Math.floor((Math.random() * 4) + 1); i < n; i++) {
            var index = Math.floor((Math.random() * 4));
            this.stacked.push({
                value: Math.floor((Math.random() * 30) + 1),
                type: types[index]
            });
        }
    };
    /**
     * initial run for random stacked value
     */
    this.randomStacked();

    /**
     * summernoteText - used for Summernote plugin
     */
    this.summernoteText = ['<h3>Hello Jonathan! </h3>',
        '<p>dummy text of the printing and typesetting industry. <strong>Lorem Ipsum has been the dustrys</strong> standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more',
        'recently with</p>'].join('');

    /**
     * General variables for Peity Charts
     * used in many view so this is in Main controller
     */
    this.BarChart = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2, 4, 7, 3, 2, 7, 9, 6, 4, 5, 7, 3, 2, 1, 0, 9, 5, 6, 8, 3, 2, 1],
        options: {
            fill: ["#1ab394", "#d7d7d7"],
            width: 100
        }
    };

    this.BarChart2 = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.BarChart3 = {
        data: [5, 3, 2, -1, -3, -2, 2, 3, 5, 2],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.LineChart = {
        data: [5, 9, 7, 3, 5, 2, 5, 3, 9, 6, 5, 9, 4, 7, 3, 2, 9, 8, 7, 4, 5, 1, 2, 9, 5, 4, 7],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart2 = {
        data: [3, 2, 9, 8, 47, 4, 5, 1, 2, 9, 5, 4, 7],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart3 = {
        data: [5, 3, 2, -1, -3, -2, 2, 3, 5, 2],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.LineChart4 = {
        data: [5, 3, 9, 6, 5, 9, 7, 3, 5, 2],
        options: {
            fill: '#1ab394',
            stroke: '#169c81',
            width: 64
        }
    };

    this.PieChart = {
        data: [1, 5],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.PieChart2 = {
        data: [226, 360],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart3 = {
        data: [0.52, 1.561],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart4 = {
        data: [1, 4],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart5 = {
        data: [226, 134],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };
    this.PieChart6 = {
        data: [0.52, 1.041],
        options: {
            fill: ["#1ab394", "#d7d7d7"]
        }
    };

    this.logout = function (callback) {
        $http.post('/logout', {}).then(function (response) {
            window.location = "/login";
            if (response.data.name) {
                $rootScope.authenticated = false;
                window.location = "/login";
            }
            callback && callback();
        }, function () {

            callback && callback();
        });
    };

    $http.get("/me").then(function (response) {
        if (response.data) {
            $rootScope.currentUser = response.data;
        }
    });

    /**
     * Pie Chart Data
     */
    var pieData = [
        {
            label: "Bid",
            data: 210,
            color: "yellowgreen"
        },
        {
            label: "Ask",
            data: 220,
            color: "darkorange"
        }
    ];

    /**
     * Pie Chart Options
     */
    var pieOptions = {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            // content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            content: function (label, xval, yval, flotItem) {
                return "vol: " + yval;
            },
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        },
        legend: {
            show: false
        }
    };
    this.depthPieChartData = pieData;
    this.depthPieChartOptions = pieOptions;


    //idle timer

    // Custm alter
    $rootScope.customAlert = false;

    // Start watching idle
    // Idle.watch(); //already started .run()

    // function you want to fire when the user goes idle
    $rootScope.$on('IdleStart', function () {
        $rootScope.customAlert = true;

    });

    // function you want to fire when the user becomes active again
    $rootScope.$on('IdleEnd', function () {
        $rootScope.customAlert = false;
    });

    $rootScope.$on('IdleTimeout', function () {
        self.logout();
    });

    $rootScope.$on('Keepalive', function () {
        $http.get("/me").then(function (response) {
            if (response.data) {
                $rootScope.currentUser = response.data;
            }
        });
    });

    $rootScope.networth = function (user, rates) {
        var mainBalance = (!user.hasOwnProperty("mainBalance") || typeof user.mainBalance == "undefined") ? 0 : user.mainBalance;
        var holdings = user.holdings;
        var worth = 0;
        rates.BTC = 11820; //todo remove it once tested
        if (rates == null || rates.length == 0)
            return mainBalance;

        if (holdings != null) {
            for (var key in holdings) {
                if (holdings.hasOwnProperty(key)) {
                    worth += holdings[key].quantity * rates[key];
                }
            }
        }
        return mainBalance + worth;
    };

}

function loginCtrl($rootScope, $http, $location) {
    var self = this;

    var authenticate = function (credentials, callback) {

        // var headers = credentials ? {authorization : "Basic "
        // + btoa(credentials.username + ":" + credentials.password)
        // } : {};

        // $http.get('user', {headers : headers}).then(function(response) {
        //     if (response.data.name) {
        //         $rootScope.authenticated = true;
        //     } else {
        //         $rootScope.authenticated = false;
        //     }
        //     callback && callback();
        // }, function() {
        //     $rootScope.authenticated = false;
        //     callback && callback();
        // });

        $http.post('login', {username: self.username, password: self.password}).then(function (response) {
            if (response.data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }, function () {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };

    // authenticate();
    self.credentials = {};
    self.login = function () {
        authenticate(self.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/dashboards/dashboard_1");
                self.error = false;
            } else {
                $location.path("/logins");
                self.error = true;
            }
        });
    };
}

function dashboardCtrl($rootScope, $http, $sce, $uibModal) {
    var self = this;

    // set the allowed units for data grouping
    var groupingUnits = [
        // [
        //     'day',
        //     [1]
        // ],
        [
            'week',                         // unit name
            [1]                             // allowed multiples
        ]
        , [
            'month',
            [1, 2, 3, 4, 6]
        ]
    ];

    var initData = {};
    initData.ohlc = [];
    initData.volume = [];

    self.chartConfig = {

        chartType: 'stock',

        chart: {
            events: {
                load: function (event) {
                    loadChartData();
                },
                selection: function (event) {
                    console.log(event);
                }
            }
        },

        rangeSelector: {
            selected: 1,
            buttons: [{
                type: 'day',
                count: 1,
                text: '1d'
            }, {
                type: 'week',
                count: 1,
                text: '1w'
            }, {
                type: 'month',
                count: 1,
                text: '1m'
            }, {
                type: 'month',
                count: 3,
                text: '3m'
            }, {
                type: 'month',
                count: 6,
                text: '6m'
            }, {
                type: 'ytd',
                text: 'YTD'
            }, {
                type: 'year',
                count: 1,
                text: '1y'
            }, {
                type: 'all',
                text: 'All'
            }]
        },

        title: {
            text: 'BTC'
        },

        yAxis: [{
            labels: {
                align: 'right',
                x: -3
            },
            title: {
                text: 'OHLC'
            },
            height: '60%',
            lineWidth: 2,
            resize: {
                enabled: true
            }
        }, {
            labels: {
                align: 'right',
                x: -3
            },
            title: {
                text: 'Volume'
            },
            top: '65%',
            height: '35%',
            offset: 0,
            lineWidth: 2
        }],

        tooltip: {
            split: true
        },

        series: [createOhlcSeries(initData), createVolumeSeries(initData)]
    };

    function loadChartData() {
        var jsonpUrl = 'https://www.highcharts.com/samples/data/jsonp.php?filename=aapl-ohlcv.json&callback=JSON_CALLBACK';
        $sce.trustAsResourceUrl(jsonpUrl);
        $http.jsonp(jsonpUrl).then(function (response) {
            var ohlcData = arrangeData(response.data);
            self.chartConfig.series = [];
            self.chartConfig.series[0] = createOhlcSeries(ohlcData);
            self.chartConfig.series[1] = createVolumeSeries(ohlcData);
        });
    }

    function createOhlcSeries(d) {
        return {
            id: 'ohclData',
            type: 'candlestick',
            name: 'AAPL',
            data: d.ohlc,
            dataGrouping: {
                units: groupingUnits
            }
        };
    }

    function createVolumeSeries(d) {
        return {
            id: 'volumeData',
            type: 'column',
            name: 'Volume',
            data: d.volume,
            yAxis: 1,
            dataGrouping: {
                units: groupingUnits
            }
        };
    }

    function arrangeData(data) {
        var dataPaired = {},
            ohlc = [],
            volume = [];
        var i = 0,
            dataLength = data.length;
        for (i; i < dataLength; i += 1) {
            ohlc.push([
                data[i][0], // the date
                data[i][1], // open
                data[i][2], // high
                data[i][3], // low
                data[i][4] // close
            ]);

            volume.push([
                data[i][0], // the date
                data[i][5] // the volume
            ]);
        }
        dataPaired.ohlc = ohlc;
        dataPaired.volume = volume;

        return dataPaired;
    }

    self.depthChartConfig = {
        chart: {
            // plotBackgroundColor: null,
            // plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Depth Chart'
        },
        tooltip: {
            pointFormat: '<b>{point.y:.1f}</b>'
        },
        plotOptions: {
            pie: {
                // allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            // name: 'Depth',
            // colorByPoint: true,
            data: [{
                name: 'Ask',
                y: 80567.33
            }, {
                name: 'Bid',
                y: 78983.000896
            }]
        }]
    };


    /**
     * Pie Chart Data
     */
    var pieData = [
        {
            label: "Bid",
            data: 0,
            color: "#1ab394"
        },
        {
            label: "Ask",
            data: 0,
            color: "#bababa"
        }
    ];

    /**
     * Pie Chart Options
     */
    var pieOptions = {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            // content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            content: function (label, xval, yval, flotItem) {
                return "vol: " + yval;
            },
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        },
        legend: {
            show: false
        }
    };
    // self.depthChartCallback = function (plot) {
    //     console.log(plot.getData());
    //     var series = plot.getData();
    //     series[0].data = self.depth.bidQuantityTotal;
    //     series[1].data = self.depth.askQuantityTotal;
    //
    //     plot.setData(series);
    //     plot.draw();
    // };
    self.depthPieChartData = pieData;
    self.depthPieChartOptions = pieOptions;

    self.depth = {};
    self.refreshDepthNow = function () {
        refreshDepth();
    };
    refreshDepth();

    function refreshDepth() {
        $http.get('stats/depth').then(function (response) {
            if (response.data) {
                self.depth = response.data;
                pieData[0].data = self.depth.bidQuantityTotal;
                pieData[1].data = self.depth.askQuantityTotal;
            }
        });
    }

    $http.get('trade_orders', {
        params: {
            size: 5,
            sort: 'id,desc'
        }
    }).then(function (response) {
        if (response.data) {
            self.recentTradeOrders = response.data.content;
        }
    });
    $http.get('trade_transactions', {
        params: {
            size: 5,
            sort: 'id,desc'
        }
    }).then(function (response) {
        if (response.data) {
            self.recentTrades = response.data.content;
        }
    });

    this.openBuyOrSell = function (side, depth, size) {
        var modalInstance = $uibModal.open({
            templateUrl: 'views/modal_place_order.html',
            size: size,
            resolve: {
                side: function () {
                    return side;
                },
                depth: function () {
                    return depth;
                }
            },
            controller: buyModalCtrl
        });

        function buyModalCtrl($scope, $uibModalInstance, $timeout, $http, side, depth) {
            $scope.depth = depth;
            var price = 0;
            if (side == 'BUY') {
                price = (depth.asks.length == 0) ? 0 : depth.asks[0].price;
            } else {
                price = (depth.bids.length == 0) ? 0 : depth.bids[0].price;
            }

            $scope.order = {
                side: side,
                symbol: 'BTC',
                type: 'LIMIT',
                price: price,
                quantity: 0
            };

            // $scope.signupForm = function () {
            //     if ($scope.signup_form.$valid) {
            //         // Submit as normal
            //     } else {
            //         $scope.signup_form.submitted = true;
            //     }
            // }

            $scope.addToBasket = function () {
                $scope.loadingAddToBasket = true;

                $timeout(function () {
                    // Simulate some service and stop loading
                    $scope.loadingAddToBasket = false;
                    $uibModalInstance.close();
                }, 2000);
            };

            $scope.placeOrder = function () {
                if ($scope.place_order_form.$valid) {
                    // Submit as normal
                    $scope.loadingPlaceOrder = true;

                    $http.post("/trade_orders", $scope.order).then(function (response) {
                        $scope.loadingPlaceOrder = false;
                        if (response.data) {

                            $uibModalInstance.close();
                        }
                    });
                } else {
                    $scope.place_order_form.submitted = true;
                }

            };

            $scope.ok = function () {
                $scope.loadingPlaceOrder = true;

                $timeout(function () {
                    // Simulate some service and stop loading
                    $scope.loadingPlaceOrder = false;
                    $uibModalInstance.close();
                }, 2000);

            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }
    }


}

function tradeBookCtrl($scope, DTOptionsBuilder, DTColumnBuilder, $compile) {

    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'ExampleFile'},
            {extend: 'pdf', title: 'ExampleFile'},

            {
                extend: 'print',
                customize: function (win) {
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ]).withOption('ajax', {
            // Either you specify the AjaxDataProp here
            dataSrc: 'content',
            url: '/trade_transactions',
            type: 'GET',
            data: function (data) {
                data.size = data.length;
                data.page = Math.floor(data.start / data.length);
                if (data.order.length != 0) {
                    var dir = (data.order[0].dir == 'dsc') ? 'desc' : data.order[0].dir;
                    data.sort = data.columns[data.order[0].column].data + ',' + dir;
                }
                return data;
            },
            dataFilter: function (data) {
                var json = jQuery.parseJSON(data);
                json.recordsTotal = json.totalElements;
                json.recordsFiltered = json.totalElements;

                return JSON.stringify(json); // return JSON string
            }
        })
        // .withOption('pagingType', 'scrolling')
        .withOption('processing', true)
        .withOption('serverSide', true)
        .withOption('order', [[1, 'dsc']]);

    $scope.dtColumns = [
        DTColumnBuilder.newColumn(null).withOption('defaultContent', '').withOption('ng-click', 'click()').withClass('details-control fa fa-lg').notSortable(),
        DTColumnBuilder.newColumn('id').withTitle('ID'),
        DTColumnBuilder.newColumn('symbol').withTitle('Symbol'),
        DTColumnBuilder.newColumn('bidById').withTitle('Bid By'),
        DTColumnBuilder.newColumn('bidTradeOrderId').withTitle('Bid Trade Order'),
        DTColumnBuilder.newColumn('askById').withTitle('Ask By'),
        DTColumnBuilder.newColumn('askTradeOrderId').withTitle('Ask Trade Order'),
        DTColumnBuilder.newColumn('quantity').withTitle('Qty.'),
        DTColumnBuilder.newColumn('price').withTitle('Price'),
        DTColumnBuilder.newColumn('value').withTitle('Value')
    ];

    $scope.dtInstanceCallback = dtInstanceCallback;

    function dtInstanceCallback(dtInstance) {
        // console.log(dtInstance);
        dtInstance.DataTable.on('click.dt', 'td.details-control', function (e) {
            var icon = $(this);
            var tr = icon.closest('tr');
            var row = dtInstance.DataTable.row(tr);
            var scope = $scope.$new(true);
            scope.tradeOrder = row.data();

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
                // icon.removeClass('fa-caret-down').addClass('fa-caret-right');
            } else {
                // Open this row
                row.child($compile('<div trade-book-row-detail class="clearfix"></div>')(scope)).show();
                // row.child(format(row.data())).show();
                tr.addClass('shown');
                // icon.removeClass('fa-caret-right').addClass('fa-caret-down');
            }
        });
    }

}

function tradeOrderBookCtrl($scope, DTOptionsBuilder, DTColumnBuilder, $compile) {

    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'ExampleFile'},
            {extend: 'pdf', title: 'ExampleFile'},

            {
                extend: 'print',
                customize: function (win) {
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ])
        .withOption('ajax', {
            // Either you specify the AjaxDataProp here
            dataSrc: 'content',
            url: '/trade_orders',
            type: 'GET',
            data: function (data) {
                data.size = data.length;
                data.page = Math.floor(data.start / data.length);
                if (data.order.length != 0) {
                    var dir = (data.order[0].dir == 'dsc') ? 'desc' : data.order[0].dir;
                    data.sort = data.columns[data.order[0].column].data + ',' + dir;
                }
                return data;
            },
            dataFilter: function (data) {
                var json = jQuery.parseJSON(data);
                json.recordsTotal = json.totalElements;
                json.recordsFiltered = json.totalElements;

                return JSON.stringify(json); // return JSON string
            }
        })
        // .withOption('pagingType', 'scrolling')
        .withOption('processing', true)
        .withOption('serverSide', true)
        .withOption('order', [[1, 'dsc']]);
    $scope.dtColumns = [
        DTColumnBuilder.newColumn(null).withOption('defaultContent', '').withOption('ng-click', 'click()').withClass('details-control fa fa-lg').notSortable(),
        DTColumnBuilder.newColumn('id').withTitle('ID'),
        DTColumnBuilder.newColumn('symbol').withTitle('Symbol'),
        DTColumnBuilder.newColumn('side').withTitle('BUY/SELL'),
        DTColumnBuilder.newColumn('type').withTitle('Type'),
        DTColumnBuilder.newColumn('quantity').withTitle('Qty.'),
        DTColumnBuilder.newColumn('pendingQuantity').withTitle('Pending Qty.'),
        DTColumnBuilder.newColumn('tradedQuantity').withTitle('Traded Qty.'),
        DTColumnBuilder.newColumn('price').withTitle('Price'),
        DTColumnBuilder.newColumn('value').withTitle('Value'),
        DTColumnBuilder.newColumn('status').withTitle('Status'),
        DTColumnBuilder.newColumn('validity').withTitle('Validity'),
        DTColumnBuilder.newColumn('remarks').withTitle('Remarks')
    ];

    $scope.dtInstanceCallback = dtInstanceCallback;

    function dtInstanceCallback(dtInstance) {
        // console.log(dtInstance);
        dtInstance.DataTable.on('click.dt', 'td.details-control', function (e) {
            var icon = $(this);
            var tr = icon.closest('tr');
            var row = dtInstance.DataTable.row(tr);
            var scope = $scope.$new(true);
            scope.tradeOrder = row.data();

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
                // icon.removeClass('fa-caret-down').addClass('fa-caret-right');
            } else {
                // Open this row
                row.child($compile('<div trade-order-book-row-detail class="clearfix"></div>')(scope)).show();
                // row.child(format(row.data())).show();
                tr.addClass('shown');
                // icon.removeClass('fa-caret-right').addClass('fa-caret-down');
            }
        });
    }

}

function netPositionsCtrl($scope, DTOptionsBuilder, DTColumnBuilder, $compile) {
    $scope.dtOptions = DTOptionsBuilder.newOptions()
        .withDOM('<"html5buttons"B>lTfgitp')
        .withButtons([
            {extend: 'copy'},
            {extend: 'csv'},
            {extend: 'excel', title: 'NetPositions'},
            {extend: 'pdf', title: 'NetPositions'},

            {
                extend: 'print',
                customize: function (win) {
                    $(win.document.body).addClass('white-bg');
                    $(win.document.body).css('font-size', '10px');

                    $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
                }
            }
        ])
        .withOption('ajax', {
            // Either you specify the AjaxDataProp here
            dataSrc: 'content',
            url: '/net_positions',
            type: 'GET',
            data: function (data) {
                data.size = data.length;
                data.page = Math.floor(data.start / data.length);
                if (data.order.length != 0) {
                    var dir = (data.order[0].dir == 'dsc') ? 'desc' : data.order[0].dir;
                    data.sort = data.columns[data.order[0].column].data + ',' + dir;
                }
                return data;
            },
            dataFilter: function (data) {
                var json = jQuery.parseJSON(data);
                json.recordsTotal = json.totalElements;
                json.recordsFiltered = json.totalElements;

                return JSON.stringify(json); // return JSON string
            }
        })
        // .withOption('pagingType', 'scrolling')
        .withOption('processing', true)
        .withOption('serverSide', true)
        .withOption('order', [[1, 'dsc']]);
    $scope.dtColumns = [
        DTColumnBuilder.newColumn('symbol').withTitle('Symbol'),
        DTColumnBuilder.newColumn('avgBuyPrice').withTitle('Avg. Buy Price'),
        DTColumnBuilder.newColumn('netBuyQty').withTitle('Net Buy Qty.'),
        DTColumnBuilder.newColumn('avgSellPrice').withTitle('Avg. Sell Price'),
        DTColumnBuilder.newColumn('netSellQty').withTitle('Net Sell Qty.'),
    ];

}

/**
 * translateCtrl - Controller for translate
 */
function translateCtrl($translate, $scope) {
    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
        $scope.language = langKey;
    };
}

/**
 *
 * Pass all functions into module
 */
angular
    .module('inspinia')
    .controller('MainCtrl', MainCtrl)
    .controller('loginCtrl', loginCtrl)
    .controller('dashboardCtrl', dashboardCtrl)
    .controller('tradeBookCtrl', tradeBookCtrl)
    .controller('tradeOrderBookCtrl', tradeOrderBookCtrl)
    .controller('netPositionsCtrl', netPositionsCtrl)
    .controller('translateCtrl', translateCtrl);

