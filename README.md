## Pluto

> ####模块化/组件化 实现模块项目 1.0.0版本  

## 技术涵盖

>###Kotlin语言编写

>###MVP架构

>###网络框架使用retrofit + okhttp

>###依赖注入dagger2

>###RxJava RxBus做通信框架

>###路由框架ARouter

##项目初始化
> ### App 继承 BaseApplication，并注册模块初始的组件，在registerRouter()初始化ARouter

	override fun onCreate() {
        super.onCreate()
        instance = this
        Router.registerComponent("com.alien.modulea.AAppLike")
    }

    override fun initComponentDi() {

    }

    override fun registerRouter() {
        Jumper.init(BuildConfig.DEBUG, this)
    }

> ### 在BaseApplication里配置http请求头数据（可选操作）

	override fun onCreate() {
        super.onCreate()

        // 配置httpHeaders
        baseAppComponent().httpConfig().setHttpHeaders(mutableMapOf())
    }

> ### 各模块实现自己的AppLike(继承IApplicationLike)并初始化ModuleKit 
> ### Router添加Service用于主App容器获取对应的组件

	override fun onCreate() {
        Router.getInstance().addService(BusinessAService::class.java, BusinessAServiceImpl())
        AModuleKit.getInstance().init(object : AppModuleComponentDelegate {
            override fun initAppComponent(): AppComponent {
                return DaggerModuleAAppComponent.builder()
                    .baseAppComponent(BaseModuleKit.getInstance().getComponent())
                    .build()
            }
        })
    }

## 依赖注入 DI

> ### basemodule下的di使用dagger封装了项目的依赖注入

> ### 各个模块使用dagger需要实现自己的ActivityComponent/ModuleAppComponent/AppModule

#### ActivityComponent/FragmentComponent用于inject（组件）
#### ModuleAppComponent用于提供需要注入的目标类
#### AppModule用于目标注入类的实现提供

> ### 在BaseInject基类提供获取Component的方法给Activity或者Fragment实现inject

	fun fragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
            .accountAppComponent(AModuleKit.getInstance().getComponent() as ModuleAAppComponent?)
            .build()
    }

## ARouter

> 各个模块配置
	
	kapt {
	    generateStubs = true
	    arguments {
	        arg("AROUTER_MODULE_NAME", project.getName())
	    }
	}

	dependencies {
	    kapt deps.arouter.compiler
	}

> Activity 类名注册 `@Route(path = RouterPath.MAIN_PATH)`  需要获取跳转参数的需要在Activity添加 `ARouter.getInstance().inject(this)`

	//获取跳转参数
	@JvmField
    @Autowired(name = "type")
    var type: Int = 0

> 跳转统一使用Jumper管理

	fun login(context: Context){
        ARouter.getInstance().build(RouterPath.User.PATH_LOGIN).navigation(context)
    }

## 网络框架 MVP
> RestApi对网络请求进行了分装，各模块需要提供retrofit的Service的接口，具体实现交由MVP的Model模块实现

	interface BookApiService {
	    @POST(BookUrl.BOOK_INDEX_URL)
	    fun getTodayBook(@Body body: BookHomeRequest): Observable<HttpResult<BookResponse>>
	}

	interface BookMVPModel: MVPRepository {

    	fun getTodayBook(body: BookHomeRequest): Observable<HttpResult<BookResponse>>
	}

	class BookModel @Inject constructor(val bookApiService: BookApiService): BaseRepository(), BookMVPModel {

    	override fun getTodayBook(body: BookHomeRequest) = bookApiService.getTodayBook(body)

	}

> MVP的实现 Constract协议提供View和Presenter接口

	interface BookHomeConstract {

	    interface View : BaseView<Presenter>{
	        fun onResponse(response: BookResponse?)
	    }
	
	    interface Presenter: BasePresenter<View>{
	        fun getTodayBook(uid: String)
	    }
	}

	class BookHomePresenter @Inject constructor(val model: BookMVPModel, val schedulerProvider: SchedulerProvider): BookHomeConstract.Presenter {

	    lateinit var mView: BookHomeConstract.View
	
	    override fun onAttach(view: BookHomeConstract.View) {
	        mView = view
	        getTodayBook(model.uid())
	    }
	
	    override fun onDetach() {
	
	    }
	
	    override fun getTodayBook(uid: String) {
	        model.let {
	            it.getTodayBook(BookHomeRequest(uid = uid))
	                    .compose(schedulerProvider.ioToMainObservableScheduler())
	                    .subscribe(object : HttpResponseObserver<HttpResult<BookResponse>>() {
	                        override fun onResult(result: HttpResult<BookResponse>) {
	                            mView.onResponse(result.data)
	                        }
	
	                        override fun onError(error: ApiError) {
	                            mView.onError(error)
	                        }
	                    })
	        }
	    }
	}
		

## 参考
>DaggerModules [https://github.com/xudjx/DaggerModules](https://github.com/xudjx/DaggerModules)
			
