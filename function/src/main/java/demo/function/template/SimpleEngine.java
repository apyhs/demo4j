package demo.function.template;

import artoria.collection.ReferenceHashMap;
import artoria.io.IOUtils;
import artoria.io.StringBuilderWriter;
import artoria.util.Assert;
import artoria.util.CollectionUtils;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static artoria.io.IOUtils.EOF;
import static artoria.util.Const.*;

/**
 * Simple template engine implement by jdk.
 */
class SimpleEngine {

    private static abstract class Block {
        private String rawContent;

        public Block(String rawContent) {
            if (rawContent == null) { return; }
            this.setRawContent(rawContent);
        }

        public String getRawContent() {
            return rawContent;
        }

        public void setRawContent(String rawContent) {
            Assert.notNull(rawContent, "Parameter \"rawContent\" must not null. ");
            if (!rawContent.equals(this.rawContent)) {
                this.rawContent = rawContent;
                this.handleRawContent(rawContent);
            }
        }

        protected abstract void handleRawContent(String rawContent);

        /**
         * Rendering block.
         * @param data Template filled model
         * @param writer Render result
         * @throws Exception Have more different exception
         */
        public abstract void render(Map data, Writer writer) throws Exception;

    }

    private static class Text extends Block {

        public Text(String rawContent) {
            super(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
            writer.write(this.getRawContent());
        }

    }

    private static class Escape extends Block {
        private String result;

        public Escape(String rawContent) {
            super(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
            String tmp = rawContent.substring(1);
            if (DOLLAR_SIGN.equals(tmp)
                    || POUND_SIGN.equals(tmp)
                    || BACKLASH.equals(tmp)) {
                result = tmp;
            }
            else {
                result = rawContent;
            }
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
            writer.write(result);
        }

    }

    private static class Value extends Block {
        private String stripped;

        public Value(String rawContent) {
            super(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
            stripped = rawContent.substring(2, rawContent.length() - 1);
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
            Object obj = data.get(stripped);
            writer.write(obj != null ? obj.toString() : EMPTY_STRING);
        }

    }

    private static abstract class Directive extends Block {
        private String name;
        private Boolean inner = false;
        private Boolean hasParam = false;
        private Boolean hasEnd = false;

        public Directive(String rawContent) {
            super(rawContent);
        }

        protected void setName(String name) {
            this.name = name;
        }

        protected void setInner(Boolean inner) {
            this.inner = inner;
        }

        protected void setHasParam(Boolean hasParam) {
            this.hasParam = hasParam;
        }

        protected void setHasEnd(Boolean hasEnd) {
            this.hasEnd = hasEnd;
        }

        public String getName() {
            return name;
        }

        public Boolean getInner() {
            return inner;
        }

        public Boolean getHasParam() {
            return hasParam;
        }

        public Boolean getHasEnd() {
            return hasEnd;
        }

        public abstract Directive create(String rawContent);

    }

    private static class End extends Directive {

        public End(String rawContent) {
            super(rawContent);
            this.setName("end");
            this.setInner(false);
            this.setHasParam(false);
            this.setHasEnd(false);
        }

        @Override
        public Directive create(String rawContent) {
            return new End(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
        }

    }

    private static class Else extends Directive {

        public Else(String rawContent) {
            super(rawContent);
            this.setName("else");
            this.setInner(true);
            this.setHasParam(false);
            this.setHasEnd(false);
        }

        @Override
        public Directive create(String rawContent) {
            return new Else(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
        }

    }

    private static class Elseif extends Directive {

        public Elseif(String rawContent) {
            super(rawContent);
            this.setName("elseif");
            this.setInner(true);
            this.setHasParam(true);
            this.setHasEnd(false);
        }

        @Override
        public Directive create(String rawContent) {
            return new Elseif(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {
        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
        }

    }

    private static class Foreach extends Directive {

        public Foreach(String rawContent) {
            super(rawContent);
            this.setName("foreach");
            this.setInner(false);
            this.setHasParam(true);
            this.setHasEnd(true);
        }

        @Override
        public Directive create(String rawContent) {
            return new Foreach(rawContent);
        }

        @Override
        protected void handleRawContent(String rawContent) {

        }

        @Override
        public void render(Map data, Writer writer) throws Exception {
            System.out.println("-----------------");
            System.out.println(getRawContent());
            System.out.println("-----------------");
            writer.write(this.getRawContent());
        }

    }

    private static class Lexer {
        private static final Map<String, Directive> DIRECTIVE_MAP;
        private static final List<String> DIRECTIVE_NO_PARAM;
        private static final List<String> DIRECTIVE_INNER;

        static {
            DIRECTIVE_MAP = new HashMap<String, Directive>();
            DIRECTIVE_NO_PARAM = new ArrayList<String>();
            DIRECTIVE_INNER = new ArrayList<String>();
            Lexer.register(new End(null));
            Lexer.register(new Else(null));
            Lexer.register(new Elseif(null));
            Lexer.register(new Foreach(null));
        }

        public static void register(Directive directive) {
            String name = directive.getName();
            DIRECTIVE_MAP.put(name, directive);
            if (directive.getInner()) {
                DIRECTIVE_INNER.add(POUND_SIGN + name);
            }
            if (!directive.getHasParam()) {
                DIRECTIVE_NO_PARAM.add(POUND_SIGN + name);
            }
        }

        private static int min(int num1, int num2, int num3) {
            return num1 < num2 ?
                    num1 < num3 ? num1 : num3 :
                    num2 < num3 ? num2 : num3;
        }

        private static int checkBoundAndGetEnd(String content, int begin) throws ParseException {
            int check = content.indexOf("${", begin);
            if (check != begin) {
                throw new ParseException("After \"$\" must be \"{\" in index \"" + begin + "\". ", begin);
            }
            int end = content.indexOf(RIGHT_CURLY_BRACKET, begin);
            if (end == EOF) {
                throw new ParseException("After \"${\" must be \"}\" in index \"" + begin + "\". ", begin);
            }
            return end;
        }

        private static void checkInnerDirective(String content, int begin) throws ParseException {
            for (String directive : DIRECTIVE_INNER) {
                int tmp = content.indexOf(directive, begin);
                if (begin == tmp) {
                    throw new ParseException("Directive \"" + directive + "\" must not start in index \"" + begin + "\". ", begin);
                }
            }
        }

        private static Directive checkBoundAndGetDirect(String content, int begin) throws ParseException {
            int tmpInt = content.indexOf(LEFT_PARENTHESIS, begin);
            Directive result = null;
            if (tmpInt == EOF) {
                for (String directive : DIRECTIVE_NO_PARAM) {
                    tmpInt = content.indexOf(directive, begin);
                    if (tmpInt != begin) { continue; }
                    result = DIRECTIVE_MAP.get(directive.substring(1));
                    break;
                }
                if (result == null) {
                    throw new ParseException("# mei you ( bing qie ye bushi NO_PARAM_DIRECTIVE", begin);
                }
            }
            else {
                String directive = content.substring(begin + 1, tmpInt);
                result = DIRECTIVE_MAP.get(directive);
                if (result == null) {
                    throw new ParseException("Nonsupport directive", begin);
                }
            }
            return result;
        }

        public static List<Block> parse(String content) throws ParseException {
            List<Block> blocks = new ArrayList<Block>();
            int finish = content.length();
            int begin = 0, end;
            while (begin != finish) {
                int backlashIndex = content.indexOf(BACKLASH, begin);
                int dollarIndex = content.indexOf(DOLLAR_SIGN, begin);
                int poundIndex = content.indexOf(POUND_SIGN, begin);
                backlashIndex = backlashIndex != EOF ? backlashIndex : finish;
                dollarIndex = dollarIndex != EOF ? dollarIndex : finish;
                poundIndex = poundIndex != EOF ? poundIndex : finish;
                int min = min(backlashIndex, dollarIndex, poundIndex);
                if (backlashIndex == min && begin == min) {
                    String tmp = content.substring(begin, (end = begin + 2));
                    blocks.add(new Escape(tmp));
                }
                else if (dollarIndex == min && begin == min) {
                    end = Lexer.checkBoundAndGetEnd(content, begin);
                    String tmp = content.substring(begin, ++end);
                    blocks.add(new Value(tmp));
                }
                else if (poundIndex == min && begin == min) {
                    Lexer.checkInnerDirective(content, begin);
                    Directive direct = Lexer.checkBoundAndGetDirect(content, begin);
                    int tmpInt;
                    if (direct.getHasEnd()) {
                        int needEnd = 1, findEnd = 0, nextPound = begin;
                        do {
                            // TODO --------
                            nextPound = content.indexOf(POUND_SIGN, nextPound + 1);
                            if (nextPound == EOF) {
                                throw new ParseException("directive hasEnd but not find", begin);
                            }
                            tmpInt = content.indexOf("#end", nextPound);
                            if (nextPound == tmpInt) {
                                findEnd++;
                            }
                            else {
                                tmpInt = content.indexOf("#elseif", nextPound);
                                if (nextPound == tmpInt) { continue; }
                                tmpInt = content.indexOf("#else", nextPound);
                                if (nextPound == tmpInt) { continue; }
                                tmpInt = content.indexOf(LEFT_PARENTHESIS, nextPound);
                                if (tmpInt == EOF) {
                                    throw new ParseException("# mei you (", nextPound);
                                }
                                String directiveTmp = content.substring(nextPound + 1, tmpInt);
                                Directive directiveObjTmp = DIRECTIVE_MAP.get(directiveTmp);
                                if (directiveObjTmp == null) {
                                    throw new ParseException("Nonsupport directive", nextPound);
                                }
                                if (directiveObjTmp.getHasEnd()) {
                                    needEnd++;
                                }
                            }
                            // TODO --------
                        } while (needEnd != findEnd);
                        String tmpStr = content.substring(begin, (end = nextPound + 4));
                        blocks.add(direct.create(tmpStr));
                    }
                    else {
                        Boolean hasParam = direct.getHasParam();
                        tmpInt = hasParam ? content.indexOf(RIGHT_PARENTHESIS, begin)
                                : begin + direct.getName().length();
                        if (hasParam && tmpInt == EOF) {
                            throw new ParseException("#Directive( mei you )", begin);
                        }
                        String tmpStr = content.substring(begin, (end = ++tmpInt));
                        blocks.add(direct.create(tmpStr));
                    }
                }
                else {
                    String tmp = content.substring(begin, (end = min));
                    blocks.add(new Text(tmp));
                }
                begin = end;
            }
            return blocks;
        }

    }

    private static class Template {
        private final List<Block> blocks = new ArrayList<Block>();
        private String name;
        private String content;

        public Template(String name, String content) {
            Assert.notBlank(name, "Parameter \"name\" must not blank. ");
            Assert.notBlank(content, "Parameter \"content\" must not blank. ");
            this.name = name;
            this.content = content;
        }

        public Template(String name, Reader reader) throws IOException {
            Assert.notBlank(name, "Parameter \"name\" must not blank. ");
            Assert.notNull(reader, "Parameter \"reader\" must not null. ");
            this.name = name;
            StringBuilderWriter writer = new StringBuilderWriter();
            IOUtils.copyLarge(reader, writer);
            this.content = writer.toString();
            Assert.notBlank(this.content, "Template reader must not blank. ");
        }

        public void render(Object data, Writer writer) throws Exception {
            Assert.notNull(data, "Parameter \"data\" must not null. ");
            Assert.isInstanceOf(Map.class, data, "Parameter \"data\" must instance of \"Map.class\". ");
            Assert.notNull(writer, "Parameter \"writer\" must not null. ");
            if (CollectionUtils.isEmpty(blocks)) {
                synchronized (blocks) {
                    if (CollectionUtils.isEmpty(blocks)) {
                        List<Block> parse = Lexer.parse(content);
                        blocks.addAll(parse);
                    }
                }
            }
            Map dataMap = (Map) data;
            for (Block block : blocks) {
                block.render(dataMap, writer);
            }
        }

    }

    private final Map<String, Template> templateCache =
            new ReferenceHashMap<String, Template>(ReferenceHashMap.Type.SOFT);

    private Template getFromCache(String name) {
        synchronized (templateCache) {
            return templateCache.get(name);
        }
    }

    private Template takeTemplate(String name, String encoding) throws IOException {
        Template template = this.getFromCache(name);
        if (template != null) { return template; }
        InputStream in = null;
        synchronized (templateCache) {
            if ((template = templateCache.get(name)) != null) {
                return template;
            }
            try {
                in = IOUtils.findClasspath(name);
                if (in == null) { return null; }
                Reader reader = new InputStreamReader(in, encoding);
                template = new Template(name, reader);
                templateCache.put(name, template);
            }
            finally {
                IOUtils.closeQuietly(in);
            }
        }
        return template;
    }

    public void render(String name, String encoding, Object data, Writer writer) throws Exception {
        Assert.notBlank(name, "Parameter \"name\" must not blank. ");
        Assert.notBlank(encoding, "Parameter \"encoding\" must not blank. ");
        Template template = this.takeTemplate(name, encoding);
        if (template == null) {
            throw new IOException("Can not find template by \"" + name + "\" in classpath. ");
        }
        template.render(data, writer);
    }

    public void render(Object data, Writer writer, String logTag, Reader reader) throws Exception {
        Template template = this.getFromCache(logTag);
        if (template != null) {
            template.render(data, writer);
            return;
        }
        synchronized (templateCache) {
            if ((template = templateCache.get(logTag)) == null) {
                template = new Template(logTag, reader);
                templateCache.put(logTag, template);
            }
        }
        template.render(data, writer);
    }

}
