import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {

    private static int start=5;
   private  static  int count=2;
    public static  void main(String args[])
    {
//        observableWithJust();
//        observableFromIterable();
//        observableWithCreate();

//        subscribeToObservable();

//        coldObserverCreate();
//        connectHotAndCold();
//        throwException();

//        createObservableWithEmpty();
//        observableRange();

//        observableDefer();
//          SingleObservable();

//        maybeObservable();

//        createCompleteable();

//        mapOperator();
//        filterOperator();
        combineMapAndFilter();
    }




    // CREATION OF OBSERVABLE


    //FIRST WAY
    private static void observableWithJust()
    {
        Observable<Integer> observable=Observable.just(1,2,3);
        observable.subscribe(item->System.out.println(item));
    }
    //SECOND WAY
    private static void observableFromIterable()
    {
        List<Integer> list= Arrays.asList(1,2,3,4);
        Observable<Integer>observable=Observable.fromIterable(list);
        observable.subscribe(item->System.out.println(item));

    }
    //THIRD WAY
    private static void observableWithCreate()
    {
        Observable<Integer>observable=Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onNext(4);
            emitter.onNext(5);
            emitter.onComplete();
        });
        observable.subscribe(item->System.out.println(item),
                error-> System.out.println("error"+error.getLocalizedMessage()),()-> System.out.println("completed"));
    }

    // WORKING WITH OBSERVER CREATION

    private static  void subscribeToObservable()
    {
        Observable<Integer>observable=Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onNext(4);
            emitter.onNext(5);
            //if you below line it will callbacjk onerror method
//            emitter.onNext(null);
            emitter.onComplete();
        });
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer);


            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("error");

            }

            @Override
            public void onComplete() {
                System.out.println("completed");

            }
        };
        observable.subscribe(observer);

    }

    // COLD OBSERVER
    private static void coldObserverCreate()
    {
        Observable<Integer> observable=Observable.just(1,2,3);
        observable.subscribe(item->System.out.println("observer1   "+item));
        observable.subscribe(item->System.out.println("observer2   "+item));

    }
    // HOTANDCOLD USING CONNECTABLE

    private static void connectHotAndCold()  {
        ConnectableObservable<Integer> observable=Observable.just(1,2,3).publish();
        observable.subscribe(item->System.out.println("observer1   "+item));

        // To subscribe and observe first connect
        observable.connect();
        // since observer2 subscribed later so missed some emission


        observable.subscribe(item->System.out.println("observer2   "+item));

    }

    //  throwException

    private static  void  throwException()
    {
//        Observable<Integer> observable=Observable.just(1,2,3);
        Observable observable=Observable.error(new Exception("AN EXCEPTION"));
        // USING SAME OBSERVABLE INSTANCES
        observable.subscribe(System.out::println,error->System.out.println("error1"+error.hashCode()));
        observable.subscribe(System.out::println,error->System.out.println("error2"+error.hashCode()));
    }

    //createrObserveableWithempty

    private static void createObservableWithEmpty()
    {
        Observable observable=Observable.empty();
        observable.subscribe(System.out::println,System.out::println,()->System.out.println("oncomplete"));
    }

    //createrObserveableWithnever
    private static void createObservableWithNever()
    {
        Observable observable=Observable.never();
        observable.subscribe(System.out::println,System.out::println,()->System.out.println("oncomplete"));
    }

    //Observablerange
    private static void observableRange()
    {
        //Prints  9 values starting from 5
        Observable<Integer> observable=Observable.range(5,9);
        observable.subscribe(System.out::println);
    }

    //ObservableDefer
    private static   void observableDefer()
    {
        Observable<Integer> observable=Observable.defer(()->Observable.range(start,count));
        observable.subscribe(item->System.out.println("observer1   "+item));
        //THIS change will be updatedd for each subscriber while using dfer
        start =9;
        count=3;
        observable.subscribe(item->System.out.println("observer2   "+item));

    }
    //SingleObservable

    private static void SingleObservable(){

        Single.just("HELOO WORLD").subscribe(System.out::println);
    }
    
    //MAYBEOBSERVABLE
    private static void maybeObservable(){
        Maybe.empty().subscribe(new MaybeObserver<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Object o) {
                System.out.println(o);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e);

            }

            @Override
            public void onComplete() {
                System.out.println("complete");

            }
        });

       
    }


    //ONCOMPLEOBSERVABLE

    private  static void createCompleteable()
    {
        Completable.fromSingle(Single.just("HELOO WORLD")).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("ONLY COMPLETE");

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }


    //MAP OPEARTER
    private static void mapOperator()
    {
        Observable<Integer> observable=Observable.just(1,2,3);
        observable
                .map(item->item*2)
                .subscribe(System.out::println);



    }


    //FITER OPERATOR
    private static void filterOperator()
    {
        Observable<Integer> observable=Observable.just(1,2,3,4,5,6,7);
        observable
                .filter(item->item%2==0)
                .subscribe(System.out::println);


    }


    //combineMapAndFilter
    private static  void combineMapAndFilter()
    {
        Observable<Integer> observable=Observable.just(1,2,3,4,5,6,7);
        observable
                .filter(item->item%2==0)
                .map(item->item*3)
                .subscribe(System.out::println);

    }







}
