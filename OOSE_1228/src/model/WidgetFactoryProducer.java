package model;

public final class WidgetFactoryProducer {
    private static WinWidgetFactory winWidgetFactory=new WinWidgetFactory();

    private WidgetFactoryProducer(){
        throw new AssertionError();
    }

    public static WidgetFactory getFactory(String environment){


        //根據Environment參數決定要Return哪個ConcreteFactory
        switch(environment) {
            case "Win": return winWidgetFactory;

        }

        return null;
    }

    public static class WinWidgetFactory implements WidgetFactory{

        @Override
        public ScrollBar createScrollbar() {
            return new WinScrollBar();
        }

        @Override
        public MenuBar createMenuBar() {
            return new WinMenuBar();
        }

        @Override
        public Menu createMenu() {
            return new WinMenu();
        }

        @Override
        public MenuItem createMenuItem() {
            return new WinMenuItem();
        }
    }

}