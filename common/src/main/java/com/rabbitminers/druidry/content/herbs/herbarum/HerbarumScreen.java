package com.rabbitminers.druidry.content.herbs.herbarum;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.rabbitminers.druidry.registry.DruidryTextures;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;

@Environment(EnvType.CLIENT)
public class HerbarumScreen extends Screen {
    public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
    public static final int PAGE_TEXT_X_OFFSET = 36;
    public static final int PAGE_TEXT_Y_OFFSET = 30;
    public static final HerbarumScreen.BookAccess EMPTY_ACCESS = new HerbarumScreen.BookAccess() {
        public int getPageCount() {
            return 0;
        }

        public FormattedText getPageRaw(int i) {
            return FormattedText.EMPTY;
        }
    };
    protected static final int TEXT_WIDTH = 114;
    protected static final int TEXT_HEIGHT = 128;
    protected static final int IMAGE_WIDTH = 253;
    protected static final int IMAGE_HEIGHT = 192;
    private HerbarumScreen.BookAccess bookAccess;
    private int currentPage;
    private List<FormattedCharSequence> cachedPageComponents;
    private int cachedPage;
    private Component pageMsg;
    private PageButton forwardButton;
    private PageButton backButton;
    private final boolean playTurnSound;

    public HerbarumScreen(HerbarumScreen.BookAccess bookAccess) {
        this(bookAccess, true);
    }

    public HerbarumScreen() {
        this(EMPTY_ACCESS, false);
    }

    private HerbarumScreen(HerbarumScreen.BookAccess bookAccess, boolean bl) {
        super(GameNarrator.NO_TITLE);
        this.cachedPageComponents = Collections.emptyList();
        this.cachedPage = -1;
        this.pageMsg = CommonComponents.EMPTY;
        this.bookAccess = bookAccess;
        this.playTurnSound = bl;
    }

    public void setBookAccess(HerbarumScreen.BookAccess bookAccess) {
        this.bookAccess = bookAccess;
        this.currentPage = Mth.clamp(this.currentPage, 0, bookAccess.getPageCount());
        this.updateButtonVisibility();
        this.cachedPage = -1;
    }

    public boolean setPage(int i) {
        int j = Mth.clamp(i, 0, this.bookAccess.getPageCount() - 1);
        if (j != this.currentPage) {
            this.currentPage = j;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        } else {
            return false;
        }
    }

    protected boolean forcePage(int i) {
        return this.setPage(i);
    }

    protected void init() {
        this.createPageControlButtons();
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        this.forwardButton = (PageButton)this.addRenderableWidget(new PageButton(i + 116, 159, true, (button) -> {
            this.pageForward();
        }, this.playTurnSound));
        this.backButton = (PageButton)this.addRenderableWidget(new PageButton(i + 43, 159, false, (button) -> {
            this.pageBack();
        }, this.playTurnSound));
        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.bookAccess.getPageCount();
    }

    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }
        this.updateButtonVisibility();
    }

    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
        }
        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
    }

    public boolean keyPressed(int i, int j, int k) {
        if (super.keyPressed(i, j, k)) {
            return true;
        } else {
            switch (i) {
                case 266:
                    this.backButton.onPress();
                    return true;
                case 267:
                    this.forwardButton.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DruidryTextures.HERBARUM_GUI.getLocation());
        int k = (this.width - IMAGE_HEIGHT) / 2;
        this.blit(poseStack, k, 2, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        if (this.cachedPage != this.currentPage) {
            FormattedText formattedText = this.bookAccess.getPage(this.currentPage);
            this.cachedPageComponents = this.font.split(formattedText, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", new Object[]{this.currentPage + 1, Math.max(this.getNumPages(), 1)});
        }

        this.cachedPage = this.currentPage;
        int m = this.font.width(this.pageMsg);
        this.font.draw(poseStack, this.pageMsg, (float)(k - m + IMAGE_HEIGHT - 44), 18.0F, 0);
        Objects.requireNonNull(this.font);
        int n = Math.min(128 / 9, this.cachedPageComponents.size());

        for(int o = 0; o < n; ++o) {
            FormattedCharSequence formattedCharSequence = (FormattedCharSequence)this.cachedPageComponents.get(o);
            Font var10000 = this.font;
            float var10003 = (float)(k + 36);
            Objects.requireNonNull(this.font);
            var10000.draw(poseStack, formattedCharSequence, var10003, (float)(32 + o * 9), 0);
        }

        Style style = this.getClickedComponentStyleAt((double)i, (double)j);
        if (style != null) {
            this.renderComponentHoverEffect(poseStack, style, i, j);
        }

        super.render(poseStack, i, j, f);
    }

    public boolean mouseClicked(double d, double e, int i) {
        if (i == 0) {
            Style style = this.getClickedComponentStyleAt(d, e);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(d, e, i);
    }

    public boolean handleComponentClicked(Style style) {
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent == null) {
            return false;
        } else if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String string = clickEvent.getValue();

            try {
                int i = Integer.parseInt(string) - 1;
                return this.forcePage(i);
            } catch (Exception var5) {
                return false;
            }
        } else {
            boolean bl = super.handleComponentClicked(style);
            if (bl && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return bl;
        }
    }

    protected void closeScreen() {
        this.minecraft.setScreen((Screen)null);
    }

    @Nullable
    public Style getClickedComponentStyleAt(double d, double e) {
        if (this.cachedPageComponents.isEmpty()) {
            return null;
        } else {
            int i = Mth.floor(d - (double)((this.width - IMAGE_HEIGHT) / 2) - 36.0);
            int j = Mth.floor(e - 2.0 - 30.0);
            if (i >= 0 && j >= 0) {
                Objects.requireNonNull(this.font);
                int k = Math.min(128 / 9, this.cachedPageComponents.size());
                if (i <= 114) {
                    Objects.requireNonNull(this.minecraft.font);
                    if (j < 9 * k + k) {
                        Objects.requireNonNull(this.minecraft.font);
                        int l = j / 9;
                        if (l >= 0 && l < this.cachedPageComponents.size()) {
                            FormattedCharSequence formattedCharSequence = (FormattedCharSequence)this.cachedPageComponents.get(l);
                            return this.minecraft.font.getSplitter().componentStyleAtWidth(formattedCharSequence, i);
                        }

                        return null;
                    }
                }

                return null;
            } else {
                return null;
            }
        }
    }

    static List<String> loadPages(CompoundTag compoundTag) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        Objects.requireNonNull(builder);
        loadPages(compoundTag, builder::add);
        return builder.build();
    }

    public static void loadPages(CompoundTag compoundTag, Consumer<String> consumer) {
        ListTag listTag = compoundTag.getList("pages", 8).copy();
        IntFunction intFunction;
        if (Minecraft.getInstance().isTextFilteringEnabled() && compoundTag.contains("filtered_pages", 10)) {
            CompoundTag compoundTag2 = compoundTag.getCompound("filtered_pages");
            intFunction = (ix) -> {
                String string = String.valueOf(ix);
                return compoundTag2.contains(string) ? compoundTag2.getString(string) : listTag.getString(ix);
            };
        } else {
            Objects.requireNonNull(listTag);
            intFunction = listTag::getString;
        }

        for(int i = 0; i < listTag.size(); ++i) {
            consumer.accept((String)intFunction.apply(i));
        }

    }

    @Environment(EnvType.CLIENT)
    public interface BookAccess {
        int getPageCount();

        FormattedText getPageRaw(int i);

        default FormattedText getPage(int i) {
            return i >= 0 && i < this.getPageCount() ? this.getPageRaw(i) : FormattedText.EMPTY;
        }

        static HerbarumScreen.BookAccess fromItem(ItemStack itemStack) {
            if (itemStack.is(Items.WRITTEN_BOOK)) {
                return new HerbarumScreen.WrittenBookAccess(itemStack);
            } else {
                return itemStack.is(Items.WRITABLE_BOOK) ? new WritableBookAccess(itemStack) : HerbarumScreen.EMPTY_ACCESS;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WritableBookAccess implements HerbarumScreen.BookAccess {
        private final List<String> pages;

        public WritableBookAccess(ItemStack itemStack) {
            this.pages = readPages(itemStack);
        }

        private static List<String> readPages(ItemStack itemStack) {
            CompoundTag compoundTag = itemStack.getTag();
            return (List)(compoundTag != null ? HerbarumScreen.loadPages(compoundTag) : ImmutableList.of());
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageRaw(int i) {
            return FormattedText.of((String)this.pages.get(i));
        }
    }

    @Environment(EnvType.CLIENT)
    public static class WrittenBookAccess implements HerbarumScreen.BookAccess {
        private final List<String> pages;

        public WrittenBookAccess(ItemStack itemStack) {
            this.pages = readPages(itemStack);
        }

        private static List<String> readPages(ItemStack itemStack) {
            CompoundTag compoundTag = itemStack.getTag();
            return (List)(compoundTag != null && WrittenBookItem.makeSureTagIsValid(compoundTag) ? HerbarumScreen.loadPages(compoundTag) : ImmutableList.of(Component.Serializer.toJson(Component.translatable("book.invalid.tag").withStyle(ChatFormatting.DARK_RED))));
        }

        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageRaw(int i) {
            String string = (String)this.pages.get(i);

            try {
                FormattedText formattedText = Component.Serializer.fromJson(string);
                if (formattedText != null) {
                    return formattedText;
                }
            } catch (Exception var4) {
            }

            return FormattedText.of(string);
        }
    }
}

