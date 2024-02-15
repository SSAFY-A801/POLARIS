<template>
  <div v-if="editor" class="p-1 mb-3 border border-gray-400 shadow-md rounded-md">
    <!-- 볼드, 이태릭, 취소선등 에디터 옵션. 아이콘은 Material icon에서 원하는 아이콘을 가져왔다. -->
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleBold().run()"
      :disabled="!editor.can().chain().focus().toggleBold().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('bold') }"
    >
      <font-awesome-icon icon="fa-solid fa-bold" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleItalic().run()"
      :disabled="!editor.can().chain().focus().toggleItalic().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('italic') }"
    >
      <font-awesome-icon icon="fa-solid fa-italic" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleStrike().run()"
      :disabled="!editor.can().chain().focus().toggleStrike().run()"
      :class="{ 'is-active bg-gray-200': editor.isActive('strike') }"
    >
      <font-awesome-icon icon="fa-solid fa-strikethrough" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
      :class="{ 'is-active bg-gray-200': editor.isActive('heading', { level: 1 }) }"
    >
      H1
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('heading', { level: 2 }) }"
    >
      H2
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('heading', { level: 3 }) }"
    >
      H3
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 4 }).run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('heading', { level: 4 }) }"
    >
      H4
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 5 }).run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('heading', { level: 5 }) }"
    >
      H5
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleHeading({ level: 6 }).run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('heading', { level: 6 }) }"
    >
      H6
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleBulletList().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('bulletList') }"
    >
      <font-awesome-icon icon="fa-solid fa-list" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleOrderedList().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('orderedList') }"
    >
      <font-awesome-icon icon="fa-solid fa-list-ol" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().undo().run()"
      :disabled="!editor.can().chain().focus().undo().run()"
    >
      <font-awesome-icon icon="fa-solid fa-rotate-left" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().redo().run()"
      :disabled="!editor.can().chain().focus().redo().run()"
    >
      <font-awesome-icon icon="fa-solid fa-rotate-right" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleCodeBlock().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('codeBlock') }"
    >
      <font-awesome-icon icon="fa-solid fa-code" />
    </button>
    <button
      id="editor-button"
      @click="editor.chain().focus().toggleBlockquote().run()"
      :class="{ 'is-active  bg-gray-200': editor.isActive('blockquote') }"
    >
      <font-awesome-icon icon="fa-solid fa-quote-right" />
    </button>
    <button id="editor-button" @click="editor.chain().focus().setHorizontalRule().run()">
      <font-awesome-icon icon="fa-solid fa-arrows-down-to-line" />
    </button>
  </div>
  <!-- 에디터 영역 -->
  <editor-content :editor="editor" class="h-80" />
</template>

<script setup lang="ts">
import { watch, ref, onBeforeUnmount } from 'vue'
import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'

const props = defineProps({
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

const editor = useEditor({
  editorProps: {
    attributes: {
      class: 'prose max-w-none'
    }
  },
  content: props.modelValue,
  extensions: [StarterKit],
  onUpdate: () => {
    emit('update:modelValue', editor.value?.getHTML())
  }
})

watch(
  () => props.modelValue,
  (value) => {
    // JSON
    // const isSame = JSON.stringify(editor.value?.getJSON()) === JSON.stringify(value)
    const isSame = editor.value?.getHTML() === value

    if (!isSame && editor.value) {
      editor.value.commands.setContent(value, false)
    }
  }
)

onBeforeUnmount(() => {
  if (editor.value) {
    editor.value.destroy()
  }
})
</script>


<style lang="scss">
#editor-button {
  @apply m-1 px-2 py-1 border-2 border-maintheme1 rounded-md font-bold;
}
.ProseMirror {
  height: 100%;
  border: 1px solid rgb(156, 161, 172);
  border-radius: 5px;
  padding: 6px;
  overflow: scroll;
}
> * + * {
  margin-top: 0.75em;
}

.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  p {
    margin: 5px;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0d0d0d;
    color: #fff;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}
</style>